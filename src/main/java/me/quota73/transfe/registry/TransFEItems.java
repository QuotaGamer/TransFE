package me.quota73.transfe.registry;

import net.minecraft.world.item.BlockItem;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TransFEItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems("transfe");
    public static DeferredItem<BlockItem> POWERGRID_FE_CONVERTER = null;
    public static DeferredItem<BlockItem> TFMG_FE_CONVERTER = null;

    public static void makeEntries() {
        if (ModList.get().isLoaded("powergrid")) POWERGRID_FE_CONVERTER = ITEMS.registerSimpleBlockItem("powergrid_converter", TransFEBlocks.POWERGRID_FE_CONVERTER);
        if (ModList.get().isLoaded("tfmg")) TFMG_FE_CONVERTER = ITEMS.registerSimpleBlockItem("tfmg_converter", TransFEBlocks.TFMG_FE_CONVERTER);
    }
}