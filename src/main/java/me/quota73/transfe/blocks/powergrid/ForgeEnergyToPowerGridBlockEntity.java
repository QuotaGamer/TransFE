package me.quota73.transfe.blocks.powergrid;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.foundation.blockEntity.behaviour.*;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import me.quota73.transfe.TransFE;
import me.quota73.transfe.registry.TransFEBlockEntities;
import net.minecraft.core.BlockPos;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.patryk3211.powergrid.electricity.base.ElectricBehaviour;
import org.patryk3211.powergrid.electricity.base.ElectricBlockEntity;
import org.patryk3211.powergrid.electricity.base.IElectricEntity;
import org.patryk3211.powergrid.electricity.sim.ElectricWire;
import org.patryk3211.powergrid.electricity.sim.node.VoltageSourceCoupling;
import org.patryk3211.powergrid.utility.Lang;

import java.util.List;

public class ForgeEnergyToPowerGridBlockEntity extends ElectricBlockEntity implements IEnergyStorage {
    private VoltageSourceCoupling        voltageSource;
    private ScrollValueBehaviour         powerSlider;
    private ElectricWire                 conn1;
//    private ElectricWire                 conn2;
    private long                         feBuffer;
    private long                         maxBuffer = 16_384L;
    private int                          target = 120;
    private int                          lastPower = -1;
    private static final long            MIN_BUFFER_VOLTAGE = 32768L;

    public ForgeEnergyToPowerGridBlockEntity(BlockPos pos, BlockState state) { super(TransFEBlockEntities.POWERGRID_FE_CONVERTER.get(), pos, state); }

    private void updateTarget(int newValue) {
        target = newValue >= 0 ? newValue : (Math.abs(newValue) * 100);
        TransFE.LOGGER.debug("Target output/input set to {}", target);
        powerSlider.setValue(newValue);
        setChanged();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        electricBehaviour = new ElectricBehaviour(this);
        behaviours.add(electricBehaviour);

        powerSlider = new ScrollValueBehaviour(Component.literal("Power Target (Watts IN / Voltage OUT depending on mode)"), this, new CenteredSideValueBoxTransform()) {
            @Override
            public ValueSettingsBoard createBoard(Player player, BlockHitResult hitResult) {
                ImmutableList<Component> rows = ImmutableList.of(Component.literal("+1"), Component.literal("+100"));
                ValueSettingsFormatter formatter = new ValueSettingsFormatter(s -> Lang.number(Math.max(0.0F, Math.abs((float) s.value()))).component());
                return new ValueSettingsBoard(this.label, 250, 20, rows, formatter);
            }

            @Override
            public void setValueSettings(Player player, ValueSettingsBehaviour.ValueSettings setting, boolean ctrl) {
                int value = Math.max(0, setting.value());
                this.setValue(setting.row() == 0 ? value : -value);
            }
        };
        powerSlider.between(-250, 250);
        powerSlider.withCallback(this::updateTarget);
        behaviours.add(powerSlider);
    }

    @Override
    public void buildCircuit(IElectricEntity.CircuitBuilder builder) {
        builder.setTerminalCount(2);

        voltageSource = new VoltageSourceCoupling(
                builder.terminalNode(0),
                builder.terminalNode(1),
                0.0f,
                0.0f
        );
        builder.add(voltageSource);

        conn1 = builder.connect(0.1f, builder.terminalNode(0), builder.terminalNode(1));
    }

    @Override
    public void electricalTick() {
        conn1.setResistance(Double.MAX_VALUE);
        if (this.level != null) {
            int power = this.level.getBestNeighborSignal(this.worldPosition);
            if (power != 0) {
                this.maxBuffer = Math.max(this.maxBuffer, 10_000_000L);
                voltageSource.setResistance(Float.MAX_VALUE);
                voltageSource.setVoltage(0);

                double v = conn1.potentialDifference();

                if (Math.round(v) != 0) conn1.setResistance(target == 0 ? Double.MAX_VALUE : Math.max((v * v) / target, 0.1d));

                this.applyPower(conn1);

                double watts = (conn1.current() * v);
                double feIn = watts * 10;
                TransFE.LOGGER.debug("[Consumer] Voltage across connections: {}V", v);
                TransFE.LOGGER.debug("[Consumer] Power drawn: {}W ({} FE)", watts, feIn);
                TransFE.LOGGER.debug("[Consumer] Power draw target: {}W", target);
                TransFE.LOGGER.debug("[Consumer] Connection resistance: {}Ω", conn1.getResistance());
                TransFE.LOGGER.debug("[Consumer] Source current: {}A", voltageSource.getCurrent());
                feBuffer = (long) Math.min(maxBuffer, feBuffer + feIn);
                if (feBuffer == maxBuffer) updateTarget(0);

            } else {
                voltageSource.setResistance(0.0f);

                float oldVoltage = TransFE.calculateVoltage(feBuffer, target, maxBuffer, MIN_BUFFER_VOLTAGE);

                float current = (float) Math.abs(voltageSource.getCurrent());
                float watts = oldVoltage * current;
                long fe = Math.round(watts * 10);
                feBuffer = Math.max(0, feBuffer - fe);
                if (fe >= (maxBuffer * 0.5)) maxBuffer += fe;

                float newVoltage = TransFE.calculateVoltage(feBuffer, target, maxBuffer, MIN_BUFFER_VOLTAGE);

                TransFE.LOGGER.debug("[Generator] Old voltage: {}V", oldVoltage);
                TransFE.LOGGER.debug("[Generator] New voltage: {}V", newVoltage);
                TransFE.LOGGER.debug("[Generator] voltageSource voltage (should be equal to old voltage): {}V", voltageSource.getVoltage());
                TransFE.LOGGER.debug("[Generator] Current draw (old): {}A", current);
                TransFE.LOGGER.debug("[Generator] Power draw (old): {}W ({} FE)", watts, fe);
                TransFE.LOGGER.debug("[Generator] Voltage across conn1 (should be equal to New): {}V", conn1.potentialDifference());
                TransFE.LOGGER.debug("[Generator] Current across conn1 (should be NOTHING): {}A", conn1.current());

                voltageSource.setVoltage(newVoltage);
                TransFE.LOGGER.debug("[Generator] voltageSource voltage (should now be equal to new voltage): {}V", voltageSource.getVoltage());
                setChanged();
            }
            lastPower = power;
        }
    }

    @Override
    public int receiveEnergy(int i, boolean bl) {
        long accepted = Math.min(i, maxBuffer - feBuffer);
        if (!bl) feBuffer += accepted;
        return (int) accepted;
    }

    @Override
    public int extractEnergy(int i, boolean bl) {
        long pulled = Math.min(feBuffer, i);
        if (!bl) feBuffer -= pulled;
        return (int) pulled;
    }

    @Override public int getEnergyStored() { return (int) this.feBuffer; }
    @Override public int getMaxEnergyStored() { return (int) maxBuffer; }
    @Override public boolean canExtract() { return this.lastPower != 0; }
    @Override public boolean canReceive() { return this.lastPower == 0; }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        compound.putLong("forge_energy", this.feBuffer);
        compound.putLong("max_forge_energy", this.maxBuffer);
        compound.putInt("last_redstone_power", this.lastPower);
        compound.putInt("power_generation", this.powerSlider.getValue());
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        this.feBuffer = compound.getLong("forge_energy");
        this.maxBuffer = compound.getLong("max_forge_energy");
        this.lastPower = compound.getInt("last_redstone_power");
        updateTarget(compound.getInt("power_generation"));
    }
}