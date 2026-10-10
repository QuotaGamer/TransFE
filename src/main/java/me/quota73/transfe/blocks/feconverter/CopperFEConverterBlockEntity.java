package me.quota73.transfe.blocks.feconverter;

import me.quota73.transfe.registry.TransFEBlockEntities;
import net.minecraft.core.BlockPos;

import net.minecraft.world.level.block.state.BlockState;

public class CopperFEConverterBlockEntity extends BaseFEConverterBlockEntity {
    private static final float INTERNAL_RESISTANCE = 7.5f;
    private static final float MAX_VOLTAGE         = 120.0f;
    private static final long  MAX_BUFFER          = 48 * 1024L;
    private static final long  MIN_BUFFER_VOLTAGE  = 4 * 1024L;

    public CopperFEConverterBlockEntity(BlockPos pos, BlockState state) {
        super(TransFEBlockEntities.COPPER_FE_CONVERTER.get(), pos, state, INTERNAL_RESISTANCE, MAX_VOLTAGE, MAX_BUFFER, MIN_BUFFER_VOLTAGE);
    }
}