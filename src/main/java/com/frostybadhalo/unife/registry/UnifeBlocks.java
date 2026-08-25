package com.frostybadhalo.unife.registry;

import com.frostybadhalo.unife.blocks.feconverter.CreativeFEConverterBlock;
import com.frostybadhalo.unife.blocks.feconverter.GoldFEConverterBlock;
import com.frostybadhalo.unife.blocks.feconverter.IronFEConverterBlock;
import com.frostybadhalo.unife.blocks.feconverter.CopperFEConverterBlock;
import com.frostybadhalo.unife.blocks.tfmgconverter.TFMGToFEConverterBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UnifeBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks("unife");

    public static final DeferredBlock<IronFEConverterBlock> IRON_FE_CONVERTER =
            BLOCKS.registerBlock("iron_fe_converter", IronFEConverterBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .strength(3.0f, 6.0f)
                            .sound(SoundType.WOOD)
                            .noOcclusion()
                            .explosionResistance(6.0f)
                            .requiresCorrectToolForDrops()
            );

    public static final DeferredBlock<GoldFEConverterBlock> GOLD_FE_CONVERTER =
            BLOCKS.registerBlock("gold_fe_converter", GoldFEConverterBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .strength(3.0f, 6.0f)
                            .sound(SoundType.WOOD)
                            .noOcclusion()
                            .explosionResistance(6.0f)
                            .requiresCorrectToolForDrops()
            );

    public static final DeferredBlock<CopperFEConverterBlock> COPPER_FE_CONVERTER =
            BLOCKS.registerBlock("copper_fe_converter", CopperFEConverterBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .strength(3.0f, 6.0f)
                            .sound(SoundType.WOOD)
                            .noOcclusion()
                            .explosionResistance(6.0f)
                            .requiresCorrectToolForDrops()
            );

    public static final DeferredBlock<CreativeFEConverterBlock> CREATIVE_FE_CONVERTER =
            BLOCKS.registerBlock("creative_fe_converter", CreativeFEConverterBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .strength(3.0f, 6.0f)
                            .sound(SoundType.WOOD)
                            .noOcclusion()
                            .explosionResistance(6.0f)
                            .requiresCorrectToolForDrops()
            );

    public static final DeferredBlock<TFMGToFEConverterBlock> TFMG_FE_CONVERTER =
            BLOCKS.registerBlock("tfmg_fe_converter", TFMGToFEConverterBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .strength(3.0f, 6.0f)
                            .sound(SoundType.WOOD)
                            .noOcclusion()
                            .explosionResistance(6.0f)
                            .requiresCorrectToolForDrops()
            );
}