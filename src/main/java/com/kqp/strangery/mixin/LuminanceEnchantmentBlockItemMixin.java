package com.kqp.strangery.mixin;

import com.kqp.strangery.init.StrangeryEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockItem.class)
public class LuminanceEnchantmentBlockItemMixin {

    @Redirect(
        method = "place",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;decrement(I)V"
        )
    )
    private void redirectDecrement(
        ItemStack itemStack,
        int amount,
        ItemPlacementContext context
    ) {
        int luminanceLevel = EnchantmentHelper.getLevel(
            StrangeryEnchantments.LUMINANCE,
            context.getStack()
        );

        if (luminanceLevel > 0) {
            // Pass
        } else {
            itemStack.decrement(1);
        }
    }
}
