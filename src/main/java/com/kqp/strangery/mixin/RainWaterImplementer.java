package com.kqp.strangery.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Used to add water blocks during rain.
 */
@Mixin(value = World.class)
public class RainWaterImplementer {
    private static final int SPAWN_DISTANCE = 64;

    @Inject(method = "tickBlockEntities", at = @At("HEAD"))
    public void tick(CallbackInfo callbackInfo) {
        World world = (World) (Object) this;

        if (!world.isClient()) {
            if (world.isRaining()) {
                for (PlayerEntity player : world.getPlayers()) {
                    if (player.age % 20 != 0) {
                        continue;
                    }

                    BlockPos playerPos = player.getBlockPos();
                    int xOffset = SPAWN_DISTANCE - world.random.nextInt(SPAWN_DISTANCE * 2);
                    int zOffset = SPAWN_DISTANCE - world.random.nextInt(SPAWN_DISTANCE * 2);
                    int x = playerPos.getX() + xOffset;
                    int z = playerPos.getZ() + zOffset;

                    BlockPos blockPos = getRainWaterBlock(x, z);
                    System.out.println(blockPos);

                    if (blockPos != null) {
                        world.setBlockState(
                            blockPos,
                            Blocks.WATER.getDefaultState().with(
                                FluidBlock.LEVEL, 8
                            )
                        );
                    }
                }
            }
        }
    }

    private BlockPos getRainWaterBlock(int x, int z) {
        World world = (World) (Object) this;

        BlockPos.Mutable blockPos = new BlockPos.Mutable(x, 256, z);
        while (blockPos.getY() > 0) {
            if (world.isSkyVisible(blockPos) && !world.isAir(blockPos)) {
                return blockPos.add(0, 8, 0);
            } else {
                blockPos.move(0, -1, 0);
            }
        }

        return null;
    }
}
