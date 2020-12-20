package com.kqp.strangery.mixin;

import com.kqp.strangery.init.StrangeryStatusEffects;
import com.kqp.strangery.init.data.StrangeryConfig;
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
 * Used to add player debuffs.
 */
@Mixin(ServerPlayerEntity.class)
public class PlayerDebuffsAdder {

    private static final int DARKNESS_DEBUFF_THRESHOLD = 5 * 60 * 20;
    private static final int SLEEP_DEBUFF_THRESHOLD = 60 * 60 * 20;
    private static final int UNDERGROUND_DEBUFF_THRESHOLD = 30 * 60 * 20;

    private int darknessTimer = 0;
    private int undergroundTimer = 0;

    @Inject(method = "playerTick", at = @At("HEAD"))
    private void injectPlayerTick(CallbackInfo callbackInfo) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        if (!StrangeryConfig.get().playerDebuffs) {
            return;
        }

        try {
            World world = player.getServerWorld();

            boolean triggerHallucination = false;
            boolean triggerBlindness = false;
            boolean triggerFatigue = false;
            boolean triggerWeakness = false;
            boolean triggerSlowness = false;

            // Darkness-induced hallucination
            if (
                world.getLightLevel(player.getBlockPos()) < 4 &&
                !world.isSkyVisible(player.getBlockPos())
            ) {
                darknessTimer =
                    Math.min(darknessTimer + 1, DARKNESS_DEBUFF_THRESHOLD);
            } else {
                darknessTimer = Math.max(0, darknessTimer - 1);
            }

            if (darknessTimer >= DARKNESS_DEBUFF_THRESHOLD) {
                triggerHallucination = true;
            }

            // Sleep-induced fatigue and weakness
            if (getTimeSinceSlept(player) > SLEEP_DEBUFF_THRESHOLD) {
                triggerWeakness = true;
            }

            // Hunger-induced fatigue and weakness
            if (player.getHungerManager().getFoodLevel() <= 6) {
                triggerFatigue = true;
                triggerWeakness = true;

                // Hunger-induced hallucination and slowness
                if (player.getHungerManager().getFoodLevel() == 0) {
                    triggerHallucination = true;
                    triggerSlowness = true;
                }
            }

            // Update timer for when player is underground
            if (player.getBlockPos().getY() < 16) {
                undergroundTimer =
                    Math.min(
                        undergroundTimer + 1,
                        UNDERGROUND_DEBUFF_THRESHOLD
                    );
            } else {
                undergroundTimer = Math.max(0, undergroundTimer - 1);
            }

            // Underground-induced hallucination
            if (undergroundTimer >= UNDERGROUND_DEBUFF_THRESHOLD) {
                triggerHallucination = true;
            }

            if (player.age % 20 == 0) {
                if (triggerHallucination) {
                    player.addStatusEffect(
                        new StatusEffectInstance(
                            StrangeryStatusEffects.HALLUCINATING,
                            5 * 20,
                            0,
                            true,
                            false,
                            false
                        )
                    );
                }

                if (triggerBlindness) {
                    player.addStatusEffect(
                        new StatusEffectInstance(
                            StatusEffects.BLINDNESS,
                            5 * 20,
                            0,
                            true,
                            false,
                            false
                        )
                    );
                }

                if (triggerFatigue) {
                    player.addStatusEffect(
                        new StatusEffectInstance(
                            StatusEffects.MINING_FATIGUE,
                            5 * 20,
                            0,
                            true,
                            false,
                            false
                        )
                    );
                }

                if (triggerWeakness) {
                    player.addStatusEffect(
                        new StatusEffectInstance(
                            StatusEffects.WEAKNESS,
                            5 * 20,
                            0,
                            true,
                            false,
                            false
                        )
                    );
                }

                if (triggerSlowness) {
                    player.addStatusEffect(
                        new StatusEffectInstance(
                            StatusEffects.SLOWNESS,
                            5 * 20,
                            0,
                            true,
                            false,
                            false
                        )
                    );
                }
            }
        } catch (Throwable error) {
            CrashReport crashReport = CrashReport.create(
                error,
                "Ticking player"
            );
            CrashReportSection crashReportSection = crashReport.addElement(
                "Player being ticked"
            );
            player.populateCrashReport(crashReportSection);
            throw new CrashException(crashReport);
        }
    }

    private static int getTimeSinceSlept(ServerPlayerEntity player) {
        return MathHelper.clamp(
            player
                .getStatHandler()
                .getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)),
            1,
            Integer.MAX_VALUE
        );
    }
}
