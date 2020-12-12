package com.kqp.strangery.mixin;

import com.kqp.strangery.enchantment.FrostEnchantment;
import com.kqp.strangery.enchantment.FrozenEdgeEnchantment;
import com.kqp.strangery.enchantment.WisdomEnchantment;
import net.minecraft.enchantment.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentCancelMixin {

    @Inject(method = "canAccept", at = @At("HEAD"), cancellable = true)
    private void injectCanAccept(
        Enchantment other,
        CallbackInfoReturnable<Boolean> callbackInfo
    ) {
        Enchantment enchantment = (Enchantment) (Object) this;
        if (
            (
                enchantment instanceof FireAspectEnchantment &&
                other instanceof FrozenEdgeEnchantment
            ) ||
            (
                enchantment instanceof FlameEnchantment &&
                other instanceof FrostEnchantment
            ) ||
            (
                (
                    enchantment instanceof RespirationEnchantment ||
                    enchantment instanceof AquaAffinityEnchantment
                ) &&
                other instanceof WisdomEnchantment
            )
        ) {
            callbackInfo.setReturnValue(false);
        }
    }
}
