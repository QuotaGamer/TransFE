package me.quota73.transfe.blocks.feconverter;

import me.quota73.transfe.registry.TransFEBlockEntities;
import net.minecraft.core.BlockPos;

import net.minecraft.world.level.block.state.BlockState;

public class CreativeFEConverterBlockEntity extends BaseFEConverterBlockEntity {
    private static final float INTERNAL_RESISTANCE = 0.0f;
    private static final float MAX_VOLTAGE         = 240.0f;
    private static final long  MAX_BUFFER          = Integer.MAX_VALUE;
    private static final long  MIN_BUFFER_VOLTAGE  = 0;

    // This is effectively just a static voltage output with infinite FE storage...
    // it has no reason to exist, except that I wanted it to.

    public CreativeFEConverterBlockEntity(BlockPos pos, BlockState state) {
        super(TransFEBlockEntities.CREATIVE_FE_CONVERTER.get(), pos, state, INTERNAL_RESISTANCE, MAX_VOLTAGE, MAX_BUFFER, MIN_BUFFER_VOLTAGE);
    }
}