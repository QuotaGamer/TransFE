package me.quota73.transfe.blocks.tfmg;

import com.drmangotea.tfmg.base.blocks.TFMGDirectionalBlock;
import me.quota73.transfe.registry.TransFEBlockEntities;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.patryk3211.powergrid.electricity.deviceconnector.DeviceConnectorBlock;

public class TFMGToFEConverterBlock extends TFMGDirectionalBlock
        implements IBE<TFMGToFEConverterBlockEntity>, IWrenchable {
    public TFMGToFEConverterBlock(Properties settings) { super(settings); }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState().setValue(FACING, ctx.getClickedFace().getOpposite());
    }


    @Override
    public @NotNull InteractionResult onWrenched(BlockState state, UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (!level.isClientSide) {
            Direction current = state.getValue(FACING);
            Direction next = current.getClockWise(); // rotação horizontal // English, please... I think you said rotation horizontal but I'm not 100% sure
            level.setBlock(pos, state.setValue(FACING, next), 3);
            IWrenchable.playRotateSound(level, pos);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }


    @Override
    public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        ItemStack drop = new ItemStack(this);

        if (player != null && !player.isCreative()) {
            if (!player.getInventory().add(drop)) {
                player.drop(drop, false);
            }
        }

        level.destroyBlock(pos, false);
        IWrenchable.playRotateSound(level, pos);

        return InteractionResult.SUCCESS;
    }


    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        return super.getDestroyProgress(state, player, level, pos) * ((player.getMainHandItem().getItem() instanceof AxeItem) ? 5.0f : 1.0f);
    }


    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos neighborPos = pos.relative(facing);
        BlockState neighborState = world.getBlockState(neighborPos);
        return DeviceConnectorBlock.canSupport(world, neighborPos, neighborState, facing.getOpposite())
                || neighborState.isFaceSturdy(world, neighborPos, facing.getOpposite());
    }

    @Override
    public Class<TFMGToFEConverterBlockEntity> getBlockEntityClass() {
        return TFMGToFEConverterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends TFMGToFEConverterBlockEntity> getBlockEntityType() {
        return TransFEBlockEntities.TFMG_FE_CONVERTER.get();
    }
}