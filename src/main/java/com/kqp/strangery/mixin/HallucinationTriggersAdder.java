package com.kqp.strangery.mixin;

import com.kqp.strangery.Strangery;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Used to add hallucination triggers.
 */
@Mixin(ServerPlayerEntity.class)
public class HallucinationTriggersAdder {
    // private static int SLEEP_INDUCED_HALLUCINATION_THRESHOLD = 60 * 60 * 20;
    private static int UNDERGROUND_INDUCED_HALLUCINATION_THRESHOLD = 30 * 60 * 20;

    private int undergroundTimer = 0;

    @Inject(method = "playerTick", at = @At("HEAD"))
    private void injectPlayerTick(CallbackInfo callbackInfo) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        try {
            World world = player.getServerWorld();

            boolean triggerHallucination = false;
            boolean triggerBlindness = false;

            // Darkness-induced hallucination and blindness
            if (world.getLightLevel(player.getBlockPos()) < 4
                && !world.isSkyVisible(player.getBlockPos())) {
                triggerHallucination = true;
                triggerBlindness = true;
            }

            // Sleep-induced hallucination
            // if (getTimeSinceSlept(player) > SLEEP_INDUCED_HALLUCINATION_THRESHOLD) {
            //     triggerHallucination = true;
            // }

            // Update timer for when player is underground
            if (player.getBlockPos().getY() < 16) {
                undergroundTimer = Math.min(undergroundTimer + 1, UNDERGROUND_INDUCED_HALLUCINATION_THRESHOLD);
            } else {
                undergroundTimer = Math.max(0, undergroundTimer - 1);
            }

            // Underground-induced hallucination
            if (undergroundTimer >= UNDERGROUND_INDUCED_HALLUCINATION_THRESHOLD) {
                triggerHallucination = true;
            }

            if (triggerHallucination) {
                player.addStatusEffect(new StatusEffectInstance(
                    Strangery.SE.HALLUCINATING,
                    5 * 20,
                    0,
                    true,
                    false,
                    false
                ));
            }

            if (triggerBlindness) {
                player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.BLINDNESS,
                    5 * 20,
                    0,
                    true,
                    false,
                    false
                ));
            }
        } catch (Throwable var4) {
            CrashReport crashReport = CrashReport.create(var4, "Ticking player");
            CrashReportSection crashReportSection = crashReport.addElement("Player being ticked");
            player.populateCrashReport(crashReportSection);
            throw new CrashException(crashReport);
        }
    }

    private static int getTimeSinceSlept(ServerPlayerEntity player) {
        return MathHelper.clamp(player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
    }
}
