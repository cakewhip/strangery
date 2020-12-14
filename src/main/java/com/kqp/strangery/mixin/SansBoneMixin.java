package com.kqp.strangery.mixin;

import com.kqp.strangery.init.StrangeryItems;
import com.kqp.strangery.init.StrangerySounds;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class SansBoneMixin {

    private PositionedSoundInstance sansMusicSoundInstance;
    private int endSongTimer = 0;

    @Inject(method = "attack", at = @At("HEAD"))
    private void injectAttack(Entity target, CallbackInfo callbackInfo) {
        PlayerEntity player = (PlayerEntity) (Object) this;

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
            endSongTimer = 10 * 20;

            if (sansMusicSoundInstance == null) {
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

                MinecraftClient.getInstance().getSoundManager().play(sansMusicSoundInstance);
            }
        }
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo callbackInfo) {
        endSongTimer = Math.max(0, endSongTimer - 1);

        if (sansMusicSoundInstance != null && endSongTimer == 0) {
            MinecraftClient.getInstance().getSoundManager().stop(sansMusicSoundInstance);
            sansMusicSoundInstance = null;
        }
    }
}
