package com.kqp.strangery.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Removes damage done by non-tools.
 */
@Mixin(LivingEntity.class)
public class SparringDamageAdder {
    private static final float LOW_DAMAGE = 0.000001F;

    // TODO this implementation sucks, since bows don't work and what not
    //      it would work a lot better to inject in the player class itself
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void injectDamage(DamageSource source, float amount,
                              CallbackInfoReturnable<Boolean> callbackInfo) {
        if (amount > LOW_DAMAGE) {
            if (source.getAttacker() != null && source.getAttacker() instanceof PlayerEntity) {
                PlayerEntity attacker = (PlayerEntity) source.getAttacker();
                ItemStack stack = attacker.getMainHandStack();

                if (stack.isEmpty() || !(stack.getItem() instanceof ToolItem)) {
                    callbackInfo.setReturnValue(
                        ((LivingEntity) (Object) this).damage(source, LOW_DAMAGE)
                    );
                }
            }
        }
    }
}
