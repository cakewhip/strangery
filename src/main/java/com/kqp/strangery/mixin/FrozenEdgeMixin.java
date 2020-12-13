package com.kqp.strangery.mixin;

import com.kqp.strangery.init.StrangeryEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class FrozenEdgeMixin {

    @Inject(
        method = "attack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/enchantment/EnchantmentHelper;getFireAspect(Lnet/minecraft/entity/LivingEntity;)I"
        )
    )
    private void injectAttack(Entity target, CallbackInfo callbackInfo) {
        if (target instanceof LivingEntity) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            int frozenEdgeLevel = EnchantmentHelper.getEquipmentLevel(
                StrangeryEnchantments.FROZEN_EDGE,
                player
            );

            if (frozenEdgeLevel > 0) {
                if (target.isOnFire()) {
                    target.extinguish();
                }

                ((LivingEntity) target).applyStatusEffect(
                        new StatusEffectInstance(
                            StatusEffects.SLOWNESS,
                            20,
                            frozenEdgeLevel
                        )
                    );
            }
        }
    }
}
