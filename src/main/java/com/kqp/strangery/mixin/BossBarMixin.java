package com.kqp.strangery.mixin;

import com.kqp.strangery.entity.BossMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class BossBarMixin {
    @Inject(
            method = "onStartedTrackingBy",
            at = @At("HEAD")
    )
    private void injectOnStartedTrackingBy(ServerPlayerEntity player, CallbackInfo callbackInfo) {
        if (this instanceof BossMob) {
            ServerBossBar bossBar = ((BossMob) (Object) this).getBossBar();

            if (bossBar != null) {
                bossBar.addPlayer(player);
            }
        }
    }
    @Inject(
            method = "onStoppedTrackingBy",
            at = @At("HEAD")
    )
    private void injectOnStoppedTrackingBy(ServerPlayerEntity player, CallbackInfo callbackInfo) {
        if (this instanceof BossMob) {
            ServerBossBar bossBar = ((BossMob) (Object) this).getBossBar();

            if (bossBar != null) {
                bossBar.removePlayer(player);
            }
        }
    }
}
