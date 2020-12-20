package com.kqp.strangery.mixin;

import com.kqp.strangery.item.tool.SaberItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class SaberBuffMixin {

    @Inject(
        method = "applyEnchantmentsToDamage",
        at = @At("HEAD"),
        cancellable = true
    )
    protected void injectApplyEnchantmentsToDamage(
        DamageSource source,
        float amount,
        CallbackInfoReturnable<Float> callbackInfo
    ) {
        if (source.getAttacker() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) source.getAttacker();

            if (
                attacker
                    .getEquippedStack(EquipmentSlot.MAINHAND)
                    .getItem() instanceof SaberItem
            ) {
                callbackInfo.setReturnValue(amount);
            }
        }
    }
}
