package me.quota73.transfe.registry;

import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TransFEItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems("transfe");

    public static final DeferredItem<BlockItem> IRON_FE_CONVERTER =
            ITEMS.registerSimpleBlockItem("iron_fe_converter", TransFEBlocks.IRON_FE_CONVERTER);

    public static final DeferredItem<BlockItem> GOLD_FE_CONVERTER =
            ITEMS.registerSimpleBlockItem("gold_fe_converter", TransFEBlocks.GOLD_FE_CONVERTER);

    public static final DeferredItem<BlockItem> COPPER_FE_CONVERTER =
            ITEMS.registerSimpleBlockItem("copper_fe_converter", TransFEBlocks.COPPER_FE_CONVERTER);

    public static final DeferredItem<BlockItem> CREATIVE_FE_CONVERTER =
            ITEMS.registerSimpleBlockItem("creative_fe_converter", TransFEBlocks.CREATIVE_FE_CONVERTER);

    public static final DeferredItem<BlockItem> TFMG_FE_CONVERTER =
            ITEMS.registerSimpleBlockItem("tfmg_fe_converter", TransFEBlocks.TFMG_FE_CONVERTER);
}