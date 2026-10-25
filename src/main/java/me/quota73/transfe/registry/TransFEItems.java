package me.quota73.transfe.registry;

import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TransFEItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems("transfe");

    public static final DeferredItem<BlockItem> FE_TO_POWER_GRID_CONVERTER =
            ITEMS.registerSimpleBlockItem("powergrid_converter", TransFEBlocks.FE_TO_POWER_GRID_CONVERTER);

    public static final DeferredItem<BlockItem> TFMG_FE_CONVERTER =
            ITEMS.registerSimpleBlockItem("tfmg_converter", TransFEBlocks.TFMG_FE_CONVERTER);
}