package com.kqp.strangery.mixin;

import com.kqp.strangery.init.StrangeryEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class LuminanceEnchantmentItemMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void injectUseOnBlock(
        ItemUsageContext context,
        CallbackInfoReturnable<ActionResult> callbackInfo
    ) {
        int luminanceLevel = EnchantmentHelper.getLevel(
                StrangeryEnchantments.LUMINANCE,
                context.getStack()
        );

        if (luminanceLevel > 0) {
            callbackInfo.setReturnValue(Items.TORCH.useOnBlock(context));
        }
    }
}
