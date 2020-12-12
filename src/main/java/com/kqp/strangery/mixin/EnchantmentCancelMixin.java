package com.kqp.strangery.mixin;

import com.kqp.strangery.enchantment.FrostEnchantment;
import com.kqp.strangery.enchantment.FrozenEdgeEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.enchantment.FlameEnchantment;
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
        if (
            (
                (Object) this instanceof FireAspectEnchantment &&
                other instanceof FrozenEdgeEnchantment
            ) ||
            (
                (Object) this instanceof FlameEnchantment &&
                other instanceof FrostEnchantment
            )
        ) {
            callbackInfo.setReturnValue(false);
        }
    }
}
