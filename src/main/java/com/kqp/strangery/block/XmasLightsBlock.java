package com.kqp.strangery.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class XmasLightsBlock extends VineBlock {

    public XmasLightsBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(
        BlockState state,
        ServerWorld world,
        BlockPos pos,
        Random random
    ) {}
}
