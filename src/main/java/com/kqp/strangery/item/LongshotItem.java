package com.kqp.strangery.item;

import com.kqp.strangery.Strangery.ECT;
import net.minecraft.enchantment.EnchantmentHelper;
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
        super(new Settings().group(ItemGroup.TOOLS).maxCount(1).maxDamage(256));
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

            if (player.isOnGround()) {
                int i = this.getMaxUseTime(stack) - remainingUseTicks;
                float pullProgress = getPullProgress(i);

                if ((double) pullProgress >= 0.1D) {
                    float yaw = player.yaw;
                    float pitch = player.pitch;

                    double maxVelY = calcSlingVelocity(
                        pullProgress,
                        yaw,
                        (float) Math.max(player.pitch, -15D),
                        EnchantmentHelper.getLevel(ECT.SLING, stack)
                    )
                        .y;
                    Vec3d normalVel = calcSlingVelocity(
                        pullProgress,
                        yaw,
                        pitch,
                        EnchantmentHelper.getLevel(ECT.SLING, stack)
                    );
                    double velY =
                        Math.signum(maxVelY) *
                        Math.min(Math.abs(maxVelY), Math.abs(normalVel.y));
                    Vec3d finalVel = new Vec3d(normalVel.x, velY, normalVel.z);

                    player.setVelocity(finalVel);

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

                    stack.damage(
                        1,
                        player,
                        p -> {
                            p.sendToolBreakStatus(user.getActiveHand());
                        }
                    );
                }
            }
        }
    }

    public static float getPullProgress(int useTicks) {
        return Math.min(useTicks, 30) / 30F;
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

        if (user.isOnGround()) {
            user.setCurrentHand(hand);

            return TypedActionResult.consume(itemStack);
        }

        return TypedActionResult.fail(itemStack);
    }

    @Override
    public int getEnchantability() {
        return 1;
    }

    public static Vec3d calcSlingVelocity(
        float pullProgress,
        float yaw,
        float pitch,
        int slingLevel
    ) {
        float speed =
            (pullProgress * 3.0F) *
            (1.0F + 0.5F * (slingLevel / (float) ECT.SLING.getMaxLevel()));

        return new Vec3d(
            -MathHelper.sin(yaw * 0.017453292F) *
            MathHelper.cos(pitch * 0.017453292F),
            -MathHelper.sin(pitch * 0.017453292F),
            MathHelper.cos(yaw * 0.017453292F) *
            MathHelper.cos(pitch * 0.017453292F)
        )
            .normalize()
            .multiply(speed);
    }
}
