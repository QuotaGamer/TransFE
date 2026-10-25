package me.quota73.transfe.blocks.tfmg;

import com.drmangotea.tfmg.content.electricity.storage.TFMGForgeEnergyStorage;

public abstract class TFMGEnergyStorage extends TFMGForgeEnergyStorage {
    public TFMGEnergyStorage(int capacity, int maxTransfer) { super(capacity, maxTransfer); }

    public void setCapacity(int val) { capacity = val; }
}
