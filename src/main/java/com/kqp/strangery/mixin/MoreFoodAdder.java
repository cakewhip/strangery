package com.kqp.strangery.mixin;

import com.kqp.strangery.init.data.FoodDataOverrides;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Used to add more foods.
 */
@Mixin(Item.class)
public class MoreFoodAdder {

    @Inject(method = "isFood", at = @At("HEAD"), cancellable = true)
    private void injectIsFood(CallbackInfoReturnable<Boolean> callbackInfo) {
        FoodComponent foodComponent = FoodDataOverrides.get(
            (Item) (Object) this
        );

        if (foodComponent != null) {
            callbackInfo.cancel();
            callbackInfo.setReturnValue(true);
        }
    }

    @Inject(method = "getFoodComponent", at = @At("HEAD"), cancellable = true)
    private void injectGetFoodComponent(
        CallbackInfoReturnable<FoodComponent> callbackInfo
    ) {
        FoodComponent foodComponent = FoodDataOverrides.get(
            (Item) (Object) this
        );

        if (foodComponent != null) {
            callbackInfo.cancel();
            callbackInfo.setReturnValue(foodComponent);
        }
    }
}
