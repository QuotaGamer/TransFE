package me.quota73.transfe.blocks.feconverter;

import me.quota73.transfe.TransFE;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.patryk3211.powergrid.electricity.base.ElectricBehaviour;
import org.patryk3211.powergrid.electricity.base.ElectricBlockEntity;
import org.patryk3211.powergrid.electricity.base.IElectricEntity;
import org.patryk3211.powergrid.electricity.sim.node.VoltageSourceCoupling;
import java.util.List;

public class BaseFEConverterBlockEntity extends ElectricBlockEntity implements IEnergyStorage {
    private VoltageSourceCoupling voltageSource;
    private long               feBuffer;
    private final float INTERNAL_RESISTANCE;
    private final float MAX_VOLTAGE;
    private final long  MAX_BUFFER;
    private final long  MIN_BUFFER_VOLTAGE;

    public BaseFEConverterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, float resistance, float maxVoltage, long maxBuffer, long minBufferVoltage) {
        super(type, pos, state);
        this.INTERNAL_RESISTANCE = resistance;
        this.MAX_VOLTAGE = maxVoltage;
        this.MAX_BUFFER = maxBuffer;
        this.MIN_BUFFER_VOLTAGE = minBufferVoltage;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        electricBehaviour = new ElectricBehaviour(this) {
            @Override
            public void tick() {
                super.tick();

                float oldVoltage = TransFE.calculateVoltage(feBuffer, MAX_VOLTAGE, MAX_BUFFER, MIN_BUFFER_VOLTAGE);

                float current = (float) Math.abs(voltageSource.getCurrent());
                float watts = oldVoltage * current;
                long fe = Math.round(watts * 10);
                feBuffer = Math.max(0, feBuffer - fe);

                float newVoltage = TransFE.calculateVoltage(feBuffer, MAX_VOLTAGE, MAX_BUFFER, MIN_BUFFER_VOLTAGE);

                voltageSource.setVoltage(newVoltage);
                setChanged();
            }
        };
        behaviours.add(electricBehaviour);
    }

    @Override
    public void buildCircuit(IElectricEntity.CircuitBuilder builder) {
        builder.setTerminalCount(4);

        voltageSource = new VoltageSourceCoupling(
                builder.terminalNode(0),
                builder.terminalNode(1),
                INTERNAL_RESISTANCE,
                0.0f
        );
        builder.add(voltageSource);

        builder.connect(0.1f, builder.terminalNode(0), builder.terminalNode(2));
        builder.connect(0.1f, builder.terminalNode(1), builder.terminalNode(3));
    }

    @Override
    public int receiveEnergy(int i, boolean bl) {
        long accepted = Math.min(i, MAX_BUFFER - feBuffer);
        if (!bl) feBuffer += accepted;
        return (int) accepted;
    }

    @Override
    public int extractEnergy(int i, boolean bl) { return 0; }

    @Override
    public int getEnergyStored() { return (int) this.feBuffer; }

    @Override
    public int getMaxEnergyStored() { return (int) this.MAX_BUFFER; }

    @Override
    public boolean canExtract() { return false; }

    @Override
    public boolean canReceive() { return true; }
}