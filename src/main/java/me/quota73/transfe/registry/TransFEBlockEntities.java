package me.quota73.transfe.registry;

import me.quota73.transfe.blocks.powergrid.ForgeEnergyToPowerGridBlockEntity;
import me.quota73.transfe.blocks.tfmg.TFMGToFEConverterBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TransFEBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, "transfe");
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ForgeEnergyToPowerGridBlockEntity>> POWERGRID_FE_CONVERTER = null;
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<TFMGToFEConverterBlockEntity>> TFMG_FE_CONVERTER = null;

    public static void makeEntries() {
        if (ModList.get().isLoaded("powergrid"))
            POWERGRID_FE_CONVERTER = BLOCK_ENTITIES.register("powergrid_converter", () ->
                    BlockEntityType.Builder
                            .of(ForgeEnergyToPowerGridBlockEntity::new, TransFEBlocks.POWERGRID_FE_CONVERTER.get())
                            .build(null)
            );

        if (ModList.get().isLoaded("tfmg"))
            TFMG_FE_CONVERTER = BLOCK_ENTITIES.register("tfmg_converter", () ->
                    BlockEntityType.Builder
                            .of(TFMGToFEConverterBlockEntity::new, TransFEBlocks.TFMG_FE_CONVERTER.get())
                            .build(null)
            );
    }
}