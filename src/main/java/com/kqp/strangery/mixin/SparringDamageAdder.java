package com.kqp.strangery.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Removes damage done by non-tools.
 */
@Mixin(PlayerEntity.class)
public class SparringDamageAdder {
    private static final float LOW_DAMAGE = 0.000001F;

    @Redirect(
        method = "attack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
        )
    )
    private boolean redirectDamage(Entity target, DamageSource source, float amount) {
        if (amount > LOW_DAMAGE) {
            if (source.getAttacker() != null && source.getAttacker() instanceof PlayerEntity) {
                PlayerEntity attacker = (PlayerEntity) source.getAttacker();
                ItemStack stack = attacker.getMainHandStack();

                if (stack.isEmpty() || !(stack.getItem() instanceof ToolItem)) {
                    amount = LOW_DAMAGE;
                }
            }
        }

        return target.damage(source, amount);
    }
}
