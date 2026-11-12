package me.quota73.transfe.registry;

import me.quota73.transfe.blocks.powergrid.ForgeEnergyToPowerGridBlock;
import me.quota73.transfe.blocks.tfmg.TFMGToFEConverterBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TransFEBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks("transfe");
    public static DeferredBlock<ForgeEnergyToPowerGridBlock> POWERGRID_FE_CONVERTER = null;
    public static DeferredBlock<TFMGToFEConverterBlock> TFMG_FE_CONVERTER = null;

    public static void makeEntries() {
        if (ModList.get().isLoaded("powegrid"))
            POWERGRID_FE_CONVERTER = BLOCKS.registerBlock("powergrid_converter", ForgeEnergyToPowerGridBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .strength(3.0f, 6.0f)
                            .sound(SoundType.WOOD)
                            .noOcclusion()
                            .explosionResistance(6.0f)
                            .requiresCorrectToolForDrops()
            );

        if (ModList.get().isLoaded("tfmg"))
            TFMG_FE_CONVERTER = BLOCKS.registerBlock("tfmg_converter", TFMGToFEConverterBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .strength(3.0f, 6.0f)
                            .sound(SoundType.WOOD)
                            .noOcclusion()
                            .explosionResistance(6.0f)
                            .requiresCorrectToolForDrops()
            );
    }
}