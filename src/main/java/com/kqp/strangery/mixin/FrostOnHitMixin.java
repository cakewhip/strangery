package com.kqp.strangery.mixin;

import com.kqp.strangery.interf.PersistentProjectileEntityInterface;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public class FrostOnHitMixin implements PersistentProjectileEntityInterface {

    private int frostLevel = 0;

    @Inject(method = "onHit", at = @At("HEAD"))
    private void injectOnEntityHit(
        LivingEntity target,
        CallbackInfo callbackInfo
    ) {
        if (frostLevel > 0) {
            if (target.isOnFire()) {
                target.extinguish();
            }

            target.applyStatusEffect(
                new StatusEffectInstance(StatusEffects.SLOWNESS, 20, frostLevel)
            );
        }
    }

    @Override
    public void setFrostLevel(int frostLevel) {
        this.frostLevel = frostLevel;
    }

    @Override
    public int getFrostLevel() {
        return this.frostLevel;
    }
}
