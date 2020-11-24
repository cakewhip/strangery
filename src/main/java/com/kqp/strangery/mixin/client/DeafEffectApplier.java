package com.kqp.strangery.mixin.client;

import com.kqp.strangery.Strangery;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Used to implement the deaf status effect.
 */
@Mixin(value = SoundSystem.class, priority = 100)
public class DeafEffectApplier {
    @Inject(method = "play", at = @At("HEAD"), cancellable = true)
    public void tick(SoundInstance soundInstance, CallbackInfo callbackInfo) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (player != null) {
            if (player.hasStatusEffect(Strangery.SE.DEAF)) {
                callbackInfo.cancel();
            }
        }
    }
}
