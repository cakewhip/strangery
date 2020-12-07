package com.kqp.strangery.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class StarBlock extends Block {

    public static final VoxelShape SHAPE = Block.createCuboidShape(
        2.0D,
        0.0D,
        2.0D,
        14.0D,
        14.0D,
        14.0D
    );

    public StarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(
        BlockState state,
        BlockView world,
        BlockPos pos,
        ShapeContext context
    ) {
        return SHAPE;
    }

    @Override
    public boolean isTranslucent(
        BlockState state,
        BlockView world,
        BlockPos pos
    ) {
        return state.getFluidState().isEmpty();
    }
}
