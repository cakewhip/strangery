package com.kqp.strangery.mixin;

import com.kqp.strangery.entity.mob.SansEntity;
import com.kqp.strangery.init.StrangeryItems;
import com.kqp.strangery.init.StrangerySounds;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class SansMusicMixin {

    private PositionedSoundInstance sansMusicSoundInstance;
    private int endSongTimer = 0;

    private Entity lastTarget = null;
    private boolean fightingSans = false;

    @Inject(method = "attack", at = @At("HEAD"))
    private void injectAttack(Entity target, CallbackInfo callbackInfo) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (!player.world.isClient) {
            return;
        }

        if (
            target.isAttackable() &&
            target instanceof HostileEntity &&
            !target.handleAttack(player) &&
            target.world.isClient &&
            TrinketsApi
                .getTrinketsInventory(player)
                .count(StrangeryItems.SANS_BONE) >
            0
        ) {
            endSongTimer = 8 * 20;
            lastTarget = target;
        }
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo callbackInfo) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (!player.world.isClient) {
            return;
        }

        boolean play = false;

        endSongTimer = Math.max(0, endSongTimer - 1);

        if (endSongTimer > 0 && lastTarget != null) {
            if (!lastTarget.isAlive()) {
                endSongTimer = 0;
                lastTarget = null;
            }
        }

        if (player.age % 20 == 0) {
            this.fightingSans = isFightingSans();
            System.out.println(this.fightingSans);
        }

        play = play || endSongTimer > 0;
        play = play || isFightingSans();

        if (play && sansMusicSoundInstance == null) {
            sansMusicSoundInstance = new PositionedSoundInstance(
                    StrangerySounds.SANS_MUSIC_ID,
                    SoundCategory.PLAYERS,
                    0.75F,
                    1.0F,
                    true,
                    0,
                    SoundInstance.AttenuationType.LINEAR,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    true
            );

            System.out.println("PLAY");

            MinecraftClient.getInstance().getSoundManager().play(sansMusicSoundInstance);
        } else if (!play && sansMusicSoundInstance != null) {
            MinecraftClient.getInstance().getSoundManager().stop(sansMusicSoundInstance);
            sansMusicSoundInstance = null;
        }
    }

    private boolean isFightingSans() {
        PlayerEntity player = (PlayerEntity) (Object) this;

        LivingEntity closestFightingSans = player.world.getClosestEntity(
                SansEntity.class,
                new TargetPredicate()
                .setPredicate(living -> living instanceof SansEntity)
                .setBaseMaxDistance(16.0D),
                player,
                player.getX(),
                player.getY(),
                player.getZ(),
                player.getBoundingBox().expand(16.0D)
        );

        return closestFightingSans != null;
    }
}
