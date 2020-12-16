package com.kqp.strangery.mixin;

import com.kqp.strangery.init.StrangeryEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class HighStepMixin {

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void injectTickMovement(CallbackInfo callbackInfo) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        int highStepLevel = EnchantmentHelper.getEquipmentLevel(
            StrangeryEnchantments.HIGH_STEP,
            player
        );

        if (highStepLevel > 0) {
            player.stepHeight = 1.0F;
        } else {
            player.stepHeight = 0.6F;
        }
    }
}
