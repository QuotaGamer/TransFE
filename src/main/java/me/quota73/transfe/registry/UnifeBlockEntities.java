package me.quota73.transfe.registry;

import me.quota73.transfe.blocks.feconverter.CreativeFEConverterBlockEntity;
import me.quota73.transfe.blocks.feconverter.IronFEConverterBlockEntity;
import me.quota73.transfe.blocks.feconverter.GoldFEConverterBlockEntity;
import me.quota73.transfe.blocks.feconverter.CopperFEConverterBlockEntity;
import me.quota73.transfe.blocks.tfmgconverter.TFMGToFEConverterBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UnifeBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, "unife");

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IronFEConverterBlockEntity>> IRON_FE_CONVERTER =
            BLOCK_ENTITIES.register("iron_fe_converter", () ->
                    BlockEntityType.Builder
                            .of(IronFEConverterBlockEntity::new, UnifeBlocks.IRON_FE_CONVERTER.get())
                            .build(null)
            );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GoldFEConverterBlockEntity>> GOLD_FE_CONVERTER =
            BLOCK_ENTITIES.register("gold_fe_converter", () ->
                    BlockEntityType.Builder
                            .of(GoldFEConverterBlockEntity::new, UnifeBlocks.GOLD_FE_CONVERTER.get())
                            .build(null)
            );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CopperFEConverterBlockEntity>> COPPER_FE_CONVERTER =
            BLOCK_ENTITIES.register("copper_fe_converter", () ->
                    BlockEntityType.Builder
                            .of(CopperFEConverterBlockEntity::new, UnifeBlocks.COPPER_FE_CONVERTER.get())
                            .build(null)
            );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CreativeFEConverterBlockEntity>> CREATIVE_FE_CONVERTER =
            BLOCK_ENTITIES.register("creative_fe_converter", () ->
                    BlockEntityType.Builder
                            .of(CreativeFEConverterBlockEntity::new, UnifeBlocks.CREATIVE_FE_CONVERTER.get())
                            .build(null)
            );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TFMGToFEConverterBlockEntity>> TFMG_FE_CONVERTER =
            BLOCK_ENTITIES.register("tfmg_fe_converter", () ->
                    BlockEntityType.Builder
                            .of(TFMGToFEConverterBlockEntity::new, UnifeBlocks.TFMG_FE_CONVERTER.get())
                            .build(null)
            );
}