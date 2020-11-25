package com.kqp.strangery.mixin;

import com.kqp.strangery.Strangery;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Used to add hallucination when in darkness.
 */
@Mixin(ServerPlayerEntity.class)
public class DarknessHallucinationAdder {
    private long darknessTimer = 0;

    @Inject(method = "playerTick", at = @At("HEAD"))
    private void injectPlayerTick(CallbackInfo callbackInfo) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        try {
            World world = player.getServerWorld();
            int lightLevel = world.getLightLevel(player.getBlockPos());

            if (lightLevel < 4 && !world.isSkyVisible(player.getBlockPos())) {
                darknessTimer++;
            } else {
                darknessTimer = 0;
            }

            if (darknessTimer >= 5 * 20 && darknessTimer % 20 == 0) {
                player.addStatusEffect(new StatusEffectInstance(
                    Strangery.SE.HALLUCINATING,
                    5 * 20,
                    0,
                    true,
                    false,
                    false
                ));
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
}
