package me.quota73.transfe.blocks.tfmgconverter;

import com.drmangotea.tfmg.content.electricity.base.ElectricBlockEntity;
import me.quota73.transfe.registry.TransFEBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class TFMGToFEConverterBlockEntity extends ElectricBlockEntity implements IEnergyStorage {
    public final TFMGEnergyStorage energy = new TFMGEnergyStorage(16384,  Integer.MAX_VALUE) {
        @Override
        public void onEnergyChanged(int amount, int idk) { sendStuff(); }
    };

    public TFMGToFEConverterBlockEntity(BlockPos pos, BlockState state) { super(TransFEBlockEntities.TFMG_FE_CONVERTER.get(), pos, state); }

    @Override
    public void tick() {
        super.tick();
        int usage = getPowerUsage();
        energy.setCapacity(usage * 2);
        energy.setEnergy(Math.min(energy.getEnergyStored(), usage * 2));
        receiveEnergy(usage, false);
    }

    @Override
    public float resistance() { return (float) getData().voltage / 16; }

    @Override
    public int receiveEnergy(int amount, boolean simulated) { return energy.receiveEnergy(amount, simulated); }

    @Override
    public int extractEnergy(int amount, boolean simulated) { return energy.extractEnergy(amount, simulated); }

    @Override
    public int getEnergyStored() { return energy.getEnergyStored(); }

    @Override
    public int getMaxEnergyStored() { return energy.getMaxEnergyStored(); }

    @Override
    public boolean canExtract() { return true; }

    @Override
    public boolean canReceive() { return false; }
}
