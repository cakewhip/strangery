package com.kqp.strangery.item;

import java.util.Random;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LongshotItem extends Item {

    public LongshotItem() {
        super(new Settings().group(ItemGroup.TOOLS));
    }

    @Override
    public void onStoppedUsing(
        ItemStack stack,
        World world,
        LivingEntity user,
        int remainingUseTicks
    ) {
        if (user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;

            int i = this.getMaxUseTime(stack) - remainingUseTicks;
            float pullProgress = getPullProgress(i);

            if ((double) pullProgress >= 0.1D) {
                Random random = player.getRandom();

                float speed = pullProgress * 3.0F;
                float divergence = 1.0F;

                float yaw = player.yaw;
                float pitch = player.pitch;
                float roll = 0.0F;

                Vec3d vec3d = new Vec3d(
                    -MathHelper.sin(yaw * 0.017453292F) *
                    MathHelper.cos(pitch * 0.017453292F),
                    -MathHelper.sin((pitch + roll) * 0.017453292F),
                    MathHelper.cos(yaw * 0.017453292F) *
                    MathHelper.cos(pitch * 0.017453292F)
                )
                    .normalize()
                    .add(
                        random.nextGaussian() *
                        0.007499999832361937D *
                        (double) divergence,
                        random.nextGaussian() *
                        0.007499999832361937D *
                        (double) divergence,
                        random.nextGaussian() *
                        0.007499999832361937D *
                        (double) divergence
                    )
                    .multiply(speed);

                player.setVelocity(vec3d);

                world.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.ENTITY_ARROW_SHOOT,
                    SoundCategory.PLAYERS,
                    1.0F,
                    pullProgress * 0.5F
                );
            }
        }
    }

    public static float getPullProgress(int useTicks) {
        float f = (float) useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public TypedActionResult<ItemStack> use(
        World world,
        PlayerEntity user,
        Hand hand
    ) {
        ItemStack itemStack = user.getStackInHand(hand);

        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }
}
