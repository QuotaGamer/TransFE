package me.quota73.transfe.blocks.powergrid;

import com.simibubi.create.foundation.blockEntity.behaviour.*;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import me.quota73.transfe.TransFE;
import me.quota73.transfe.registry.TransFEBlockEntities;
import net.minecraft.core.BlockPos;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.patryk3211.powergrid.electricity.base.ElectricBehaviour;
import org.patryk3211.powergrid.electricity.base.ElectricBlockEntity;
import org.patryk3211.powergrid.electricity.base.IElectricEntity;
import org.patryk3211.powergrid.electricity.sim.ElectricWire;
import org.patryk3211.powergrid.electricity.sim.node.VoltageSourceCoupling;

import java.util.List;

public class ForgeEnergyToPowerGridBlockEntity extends ElectricBlockEntity implements IEnergyStorage {
    private VoltageSourceCoupling voltageSource;
    private ScrollValueBehaviour powerSlider;
    private ElectricWire conn1;
    private ElectricWire conn2;
    private long                  feBuffer;
    private long                  maxBuffer = 16_384L;
    private int                   target = 120;
    private int                   lastPower = -1;
    private static final long     MIN_BUFFER_VOLTAGE = 32768L;

    public ForgeEnergyToPowerGridBlockEntity(BlockPos pos, BlockState state) { super(TransFEBlockEntities.FE_TO_POWER_GRID_CONVERTER.get(), pos, state); }

    private void updateTarget(int newValue) {
        target = newValue;
        powerSlider.setValue(newValue);
        setChanged();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        electricBehaviour = new ElectricBehaviour(this);
        behaviours.add(electricBehaviour);

        powerSlider = new ScrollValueBehaviour(Component.literal("Targeted Output (increments of 10V / 10W depending on mode)"), this, new CenteredSideValueBoxTransform());
        powerSlider.between(0, 500);
        powerSlider.withCallback(this::updateTarget);
        behaviours.add(powerSlider);
    }

    @Override
    public void buildCircuit(IElectricEntity.CircuitBuilder builder) {
        builder.setTerminalCount(4);

        voltageSource = new VoltageSourceCoupling(
                builder.terminalNode(0),
                builder.terminalNode(1),
                0.0f,
                0.0f
        );
        builder.add(voltageSource);

        conn1 = builder.connect(0.1f, builder.terminalNode(0), builder.terminalNode(2));
        conn2 = builder.connect(0.1f, builder.terminalNode(1), builder.terminalNode(3));
    }

    @Override
    public void electricalTick() {
        if (this.level != null) {
            int power = this.level.getBestNeighborSignal(this.worldPosition);
            if (power != 0) {
                if (lastPower == 0) {
                    voltageSource.setVoltage(0);
                    this.maxBuffer = Math.max(this.maxBuffer, 1_024_000L);
                }

                double v1 = conn1.potentialDifference();
                double v2 = conn2.potentialDifference();
                
                int divider = 1;
                if (Math.round(v1) != 0 && Math.round(v2) != 0) divider = 2;
                
                if (Math.round(v1) != 0) conn1.setResistance(target == 0 ? Double.MAX_VALUE : Math.max((v1 * v1) / ((target * 10.0) / divider), 0.1));
                if (Math.round(v2) != 0) conn2.setResistance(target == 0 ? Double.MAX_VALUE : Math.max((v2 * v2) / ((target * 10.0) / divider), 0.1));

                this.applyPower(conn1);
                this.applyPower(conn2);

                System.out.println("Conn 1: " + conn1.current());
                System.out.println("Conn 2: " + conn2.current());

                double watts = (conn1.current() * v1) + (conn2.current() * v2);
                double feIn = watts * 10;
                feBuffer = (long) Math.min(maxBuffer, feBuffer + feIn);
                if (feBuffer == maxBuffer) updateTarget(0);

            } else {
                if (lastPower != 0) {
                    conn1.setResistance(0.1f);
                    conn2.setResistance(0.1f);
                }

                float oldVoltage = TransFE.calculateVoltage(feBuffer, target, maxBuffer, MIN_BUFFER_VOLTAGE);

                float current = (float) Math.abs(voltageSource.getCurrent());
                float watts = oldVoltage * current;
                long fe = Math.round(watts * 10);
                feBuffer = Math.max(0, feBuffer - fe);
                if (fe >= (maxBuffer * 0.5)) maxBuffer += fe;

                float newVoltage = TransFE.calculateVoltage(feBuffer, target, maxBuffer, MIN_BUFFER_VOLTAGE);

                voltageSource.setVoltage(newVoltage);
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
    @Override public boolean canExtract() { return false; }
    @Override public boolean canReceive() { return true; }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        compound.putLong("forge_energy", this.feBuffer);
        compound.putLong("max_forge_energy", this.maxBuffer);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        this.feBuffer = compound.getLong("forge_energy");
        this.maxBuffer = compound.getLong("max_forge_energy");
    }
}