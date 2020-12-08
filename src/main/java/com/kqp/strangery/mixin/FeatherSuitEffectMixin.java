package com.kqp.strangery.mixin;

import com.kqp.strangery.init.StrangeryItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class FeatherSuitEffectMixin {

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void injectHandleFallDamage(
        float fallDistance,
        float damageMultiplier,
        CallbackInfoReturnable<Boolean> callbackInfo
    ) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        Item helmet = player.getEquippedStack(EquipmentSlot.HEAD).getItem();
        Item chestplate = player
            .getEquippedStack(EquipmentSlot.CHEST)
            .getItem();
        Item leggings = player.getEquippedStack(EquipmentSlot.LEGS).getItem();
        Item boots = player.getEquippedStack(EquipmentSlot.FEET).getItem();

        if (
            helmet == StrangeryItems.FEATHER_SUIT_HELMET &&
            chestplate == StrangeryItems.FEATHER_SUIT_CHESTPLATE &&
            leggings == StrangeryItems.FEATHER_SUIT_LEGGINGS &&
            boots == StrangeryItems.FEATHER_SUIT_BOOTS
        ) {
            callbackInfo.setReturnValue(false);
        }
    }
}
