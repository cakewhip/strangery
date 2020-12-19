package com.kqp.strangery.mixin;

import com.kqp.strangery.entity.mob.ZombieElfEntity;
import com.kqp.strangery.entity.mob.ZombieSantaEntity;
import com.kqp.strangery.init.StrangeryItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class PresentDropMixin {

    private static final double DROP_CHANCE = 0.02D;

    @Inject(method = "drop", at = @At("HEAD"))
    private void injectDrop(DamageSource source, CallbackInfo callbackInfo4) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (
            entity instanceof HostileEntity &&
            source.getAttacker() instanceof PlayerEntity
        ) {
            double dropChance = DROP_CHANCE;
            int count = 1;

            if (entity instanceof ZombieElfEntity) {
                dropChance *= 4.0D;
            }

            if (entity instanceof ZombieSantaEntity) {
                dropChance = 1.0D;
                count = 4 + entity.getRandom().nextInt(12);
            }

            if (entity.getRandom().nextDouble() < dropChance) {
                entity.dropStack(new ItemStack(StrangeryItems.PRESENT, count));
            }
        }
    }
}
