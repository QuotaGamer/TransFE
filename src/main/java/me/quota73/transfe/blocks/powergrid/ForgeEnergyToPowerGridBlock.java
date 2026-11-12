package me.quota73.transfe.blocks.powergrid;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import me.quota73.transfe.registry.TransFEBlockEntities;
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
import org.patryk3211.powergrid.electricity.base.DirectionalElectricBlock;
import org.patryk3211.powergrid.electricity.base.IDecoratedTerminal;
import org.patryk3211.powergrid.electricity.base.TerminalBoundingBox;
import org.patryk3211.powergrid.electricity.base.terminals.BlockStateTerminalCollection;
import org.patryk3211.powergrid.electricity.deviceconnector.DeviceConnectorBlock;

public class ForgeEnergyToPowerGridBlock extends DirectionalElectricBlock
        implements IBE<ForgeEnergyToPowerGridBlockEntity>, IWrenchable {

    private static final TerminalBoundingBox[] TERMINALS = {
            new TerminalBoundingBox(IDecoratedTerminal.POSITIVE, 13.25, 14, 1.25, 14.75, 16.75, 2.75)
                    .withColor(16726843),
            new TerminalBoundingBox(IDecoratedTerminal.NEGATIVE, 1.25, 14, 1.25, 2.75, 16.75, 2.75)
                    .withColor(3899647)
    };

    public ForgeEnergyToPowerGridBlock(Properties settings) {
        super(settings);
        setTerminalCollection(
                BlockStateTerminalCollection.builder(this)
                        .forAllStates(state -> BlockStateTerminalCollection.each(
                                TERMINALS, terminal -> {
                                    Direction facing = state.getValue(FACING);
                                    return switch (facing) {
                                        case UP -> terminal.rotateAroundX(180);
                                        case SOUTH -> terminal.rotateAroundY(180);
                                        case EAST -> terminal.rotateAroundY(90);
                                        case WEST -> terminal.rotateAroundY(-90);
                                        case DOWN, NORTH -> terminal;
                                    };
                                }
                        )).build()
        );
    }


    @Override
    public @NotNull InteractionResult onWrenched(BlockState state, UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        Direction newFacing = switch (state.getValue(FACING)) {
            case UP -> Direction.DOWN;
            case NORTH, DOWN -> Direction.SOUTH;
            case SOUTH -> Direction.EAST;
            case EAST -> Direction.WEST;
            case WEST -> Direction.UP;
        };

        level.setBlock(pos, state.setValue(FACING, newFacing), 3);

        return InteractionResult.sidedSuccess(level.isClientSide);
    }


    @Override
    public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();

        if (!level.isClientSide) {
            ItemStack drop = new ItemStack(this);

            if (player != null && !player.isCreative() && !player.getInventory().add(drop)) player.drop(drop, false);

            level.destroyBlock(pos, false);
            IWrenchable.playRotateSound(level, pos);
        }

        return InteractionResult.SUCCESS;
    }


    @Override
    public float getDestroyProgress(@NotNull BlockState state, Player player, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        ItemStack tool = player.getMainHandItem();
        if (tool.getItem() instanceof AxeItem) {
            return super.getDestroyProgress(state, player, level, pos) * 5.0f;
        }
        return super.getDestroyProgress(state, player, level, pos);
    }


    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos neighborPos = pos.relative(facing);
        BlockState neighborState = world.getBlockState(neighborPos);
        return DeviceConnectorBlock.canSupport(world, neighborPos, neighborState, facing.getOpposite())
                || neighborState.isFaceSturdy(world, neighborPos, facing.getOpposite());
    }


//    @Override public @NotNull BlockState rotate(BlockState state, Rotation rotation)
//    { return state.setValue(FACING, rotation.rotate(state.getValue(FACING))); }

    @Override public BlockState getStateForPlacement(BlockPlaceContext ctx)
    { return defaultBlockState().setValue(FACING, ctx.getClickedFace().getOpposite()); }

    @Override public Class<ForgeEnergyToPowerGridBlockEntity> getBlockEntityClass()
    { return ForgeEnergyToPowerGridBlockEntity.class; }

    @Override public BlockEntityType<? extends ForgeEnergyToPowerGridBlockEntity> getBlockEntityType()
    { return TransFEBlockEntities.POWERGRID_FE_CONVERTER.get(); }
}
