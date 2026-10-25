package me.quota73.transfe.registry;

import me.quota73.transfe.blocks.powergrid.ForgeEnergyToPowerGridBlock;
import me.quota73.transfe.blocks.tfmg.TFMGToFEConverterBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TransFEBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks("transfe");

    public static final DeferredBlock<ForgeEnergyToPowerGridBlock> FE_TO_POWER_GRID_CONVERTER =
            BLOCKS.registerBlock("fe_to_powergrid_converter", ForgeEnergyToPowerGridBlock::new,
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