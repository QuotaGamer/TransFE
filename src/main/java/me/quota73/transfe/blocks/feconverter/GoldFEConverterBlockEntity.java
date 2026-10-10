package me.quota73.transfe.blocks.feconverter;

import me.quota73.transfe.registry.UnifeBlockEntities;
import net.minecraft.core.BlockPos;

import net.minecraft.world.level.block.state.BlockState;

public class GoldFEConverterBlockEntity extends BaseFEConverterBlockEntity {
    private static final float INTERNAL_RESISTANCE = 15.0f;
    private static final float MAX_VOLTAGE         = 240.0f;
    private static final long  MAX_BUFFER          = 24 * 1024L;
    private static final long  MIN_BUFFER_VOLTAGE  = 8 * 1024L;

    public GoldFEConverterBlockEntity(BlockPos pos, BlockState state) {
        super(UnifeBlockEntities.GOLD_FE_CONVERTER.get(), pos, state, INTERNAL_RESISTANCE, MAX_VOLTAGE, MAX_BUFFER, MIN_BUFFER_VOLTAGE);
    }
}