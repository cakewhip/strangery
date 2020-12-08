package com.kqp.strangery.mixin;

import com.kqp.strangery.Strangery;
import com.kqp.strangery.entity.mob.CourierEntity;
import java.util.Random;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Spawns couriers with hate mail.
 */
@Mixin(LivingEntity.class)
public class CourierSpawningMixin {

    private static final float HATE_MAIL_COURIER_CHANCE = 0.05F;

    private static final Random RANDOM = new Random();

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void injectOnDeath(DamageSource source, CallbackInfo callbackInfo) {
        LivingEntity victim = (LivingEntity) (Object) this;
        if (
            source.getAttacker() instanceof PlayerEntity &&
            victim instanceof MobEntity
        ) {
            if (RANDOM.nextFloat() < HATE_MAIL_COURIER_CHANCE) {
                PlayerEntity attacker = (PlayerEntity) source.getAttacker();
                World world = attacker.getEntityWorld();

                BlockPos spawnPos = findSpawnPosition(
                    world,
                    attacker.getBlockPos(),
                    4
                );

                if (spawnPos != null) {
                    CourierEntity courier = Strangery.E.COURIER.create(world);
                    courier.makeHateMailCourier(attacker, (MobEntity) victim);

                    courier.updatePositionAndAngles(
                        spawnPos.getX(),
                        spawnPos.getY(),
                        spawnPos.getZ(),
                        0F,
                        0F
                    );

                    world.spawnEntity(courier);
                }
            }
        }
    }

    /**
     * Finds and returns the first valid spawn position near a given origin.
     * If unable to find one after a given amount of retries, returns null.
     *
     * @param world   world
     * @param origin  origin
     * @param retries retries
     * @return block pos
     */
    private static BlockPos findSpawnPosition(
        World world,
        BlockPos origin,
        int retries
    ) {
        if (retries == 0) {
            return null;
        }

        BlockPos.Mutable blockPos = new BlockPos.Mutable(
            origin.getX() + randomSign() * (32 + RANDOM.nextInt(64)),
            4,
            origin.getZ() + randomSign() * (32 + RANDOM.nextInt(64))
        );

        int airBlocks = 0;
        while (airBlocks < 2 && blockPos.getY() < 128) {
            if (world.isAir(blockPos)) {
                airBlocks++;
            } else {
                airBlocks = 0;
            }

            blockPos.move(0, 1, 0);
        }

        if (airBlocks == 2) {
            return blockPos;
        } else {
            return findSpawnPosition(world, origin, retries - 1);
        }
    }

    /**
     * Random sign.
     *
     * @return random sign int
     */
    private static int randomSign() {
        return RANDOM.nextBoolean() ? 1 : -1;
    }
}
