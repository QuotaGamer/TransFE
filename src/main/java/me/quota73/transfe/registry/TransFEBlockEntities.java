package me.quota73.transfe.registry;

import me.quota73.transfe.blocks.powergrid.ForgeEnergyToPowerGridBlockEntity;
import me.quota73.transfe.blocks.tfmg.TFMGToFEConverterBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TransFEBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, "transfe");

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ForgeEnergyToPowerGridBlockEntity>> FE_TO_POWER_GRID_CONVERTER =
            BLOCK_ENTITIES.register("powergrid_converter", () ->
                    BlockEntityType.Builder
                            .of(ForgeEnergyToPowerGridBlockEntity::new, TransFEBlocks.FE_TO_POWER_GRID_CONVERTER.get())
                            .build(null)
            );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TFMGToFEConverterBlockEntity>> TFMG_FE_CONVERTER =
            BLOCK_ENTITIES.register("tfmg_converter", () ->
                    BlockEntityType.Builder
                            .of(TFMGToFEConverterBlockEntity::new, TransFEBlocks.TFMG_FE_CONVERTER.get())
                            .build(null)
            );
}