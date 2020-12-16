package com.kqp.strangery.mixin;

import com.kqp.strangery.enchantment.StrangeryEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

/**
 * Redirects EnchantmentTarget calls to the StrangeryEnchantment target predicate.
 */
@Mixin(EnchantmentHelper.class)
public class StrangeryEnchantmentMixin {

    @Unique
    private static Enchantment currentEnchant;

    @Inject(
        method = "getPossibleEntries",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/enchantment/EnchantmentTarget;isAcceptableItem(Lnet/minecraft/item/Item;)Z"
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void injectIsAcceptableItem1(
        int power,
        ItemStack stack,
        boolean treasureAllowed,
        CallbackInfoReturnable callbackInfo,
        List list,
        Item item,
        boolean b,
        Iterator iterator,
        Enchantment enchantment
    ) {
        currentEnchant = enchantment;
    }

    @Redirect(
        method = "getPossibleEntries",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/enchantment/EnchantmentTarget;isAcceptableItem(Lnet/minecraft/item/Item;)Z"
        )
    )
    private static boolean redirectIsAcceptableItem1(
        EnchantmentTarget enchantmentTarget,
        Item item
    ) {
        if (currentEnchant instanceof StrangeryEnchantment) {
            return (
                (StrangeryEnchantment) currentEnchant
            ).itemTargetPredicate.test(item);
        }

        return enchantmentTarget.isAcceptableItem(item);
    }
}
