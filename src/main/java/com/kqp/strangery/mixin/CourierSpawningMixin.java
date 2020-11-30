package com.kqp.strangery.mixin;

import com.kqp.strangery.Strangery;
import com.kqp.strangery.entity.mob.CourierEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(LivingEntity.class)
public class CourierSpawningMixin {
    private static final Random RANDOM = new Random();

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void injectOnDeath(DamageSource source, CallbackInfo callbackInfo) {
        LivingEntity victim = (LivingEntity) (Object) this;
        if (source.getAttacker() instanceof PlayerEntity && victim instanceof MobEntity) {
            PlayerEntity attacker = (PlayerEntity) source.getAttacker();
            CourierEntity courier = Strangery.E.COURIER.create(victim.world);
            courier.makeHateMailCourier(
                attacker,
                (MobEntity) victim
            );
            courier.updatePositionAndAngles(
                attacker.getPos().x + (16 - RANDOM.nextInt(32)),
                attacker.getPos().y + 3,
                attacker.getPos().z + (16 - RANDOM.nextInt(32)),
                0F,
                0F
            );

            victim.world.spawnEntity(courier);
        }
    }
}
