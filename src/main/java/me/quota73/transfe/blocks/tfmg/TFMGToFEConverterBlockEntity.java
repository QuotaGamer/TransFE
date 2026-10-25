package me.quota73.transfe.blocks.tfmg;

import com.drmangotea.tfmg.config.TFMGConfigs;
import com.drmangotea.tfmg.content.electricity.base.ElectricBlockEntity;
import me.quota73.transfe.registry.TransFEBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class TFMGToFEConverterBlockEntity extends ElectricBlockEntity implements IEnergyStorage {
    public TFMGToFEConverterBlockEntity(BlockPos pos, BlockState state) { super(TransFEBlockEntities.TFMG_FE_CONVERTER.get(), pos, state); }
    public final TFMGEnergyStorage energy = new TFMGEnergyStorage(16_384,  Integer.MAX_VALUE) { @Override public void onEnergyChanged(int amount, int idk) { sendStuff(); }};
    private int redstonePower = 0;
    @Override public float resistance() { return redstonePower != 0 ? 0 : getData().voltage / 32f; }
    @Override public int receiveEnergy(int amount, boolean simulated) { return energy.receiveEnergy(amount, simulated); }
    @Override public int extractEnergy(int amount, boolean simulated) { return energy.extractEnergy(amount, simulated); }
    @Override public int getEnergyStored() { return energy.getEnergyStored(); }
    @Override public int getMaxEnergyStored() { return energy.getMaxEnergyStored(); }
    @Override public boolean canExtract() { return energy.canExtract(); }
    @Override public boolean canReceive() { return energy.canReceive(); }
    @Override public int powerGeneration() { return redstonePower > 0 ? energy.getEnergyStored() : 0; }
    @Override public int voltageGeneration() { return redstonePower > 0 ? (powerGeneration() != 0 ? 250 : 0) : 0; }

    @Override
    public void tick() {
        if (this.level != null && this.level.isClientSide) return;
        super.tick();
        System.out.println(powerGeneration());
        if (this.level != null) redstonePower = this.level.getBestNeighborSignal(this.worldPosition);
        if (redstonePower == 0) {
            int usage = getPowerUsage();
            energy.setCapacity(Math.max(1, usage * 2));
            energy.setEnergy(Math.min(energy.getEnergyStored(), usage * 2));
            receiveEnergy(usage, false);
        } else if (getNetworkPowerGeneration() != 0) {
            if (energy.getMaxEnergyStored() != 256_000) energy.setCapacity(256_000);
            this.extractEnergy((int) Math.ceil(getNetworkPowerUsage() * ((double) powerGeneration() / getNetworkPowerGeneration()) / TFMGConfigs.common().machines.FEtoWattTickConversionRate.get()), false);
        }
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        compound.putInt("forge_energy", energy.getEnergyStored());
        compound.putInt("redstone_power", redstonePower);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        energy.setEnergy(compound.getInt("forge_energy"));
        redstonePower = compound.getInt("redstone_power");
    }
}
