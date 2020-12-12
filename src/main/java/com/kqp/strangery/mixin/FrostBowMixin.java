package com.kqp.strangery.mixin;

import com.kqp.strangery.init.StrangeryEnchantments;
import com.kqp.strangery.interf.PersistentProjectileEntityInterface;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BowItem.class)
public class FrostBowMixin {

    @Inject(
        method = "onStoppedUsing",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setProperties(Lnet/minecraft/entity/Entity;FFFFF)V"
        ),
        locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    private void injectOnStoppedUsing(
        ItemStack stack,
        World world,
        LivingEntity user,
        int remainingUseTicks,
        CallbackInfo callbackInfo,
        PlayerEntity player,
        boolean bl,
        ItemStack itemStack,
        int i,
        float f,
        boolean bl2,
        ArrowItem arrowItem,
        PersistentProjectileEntity persistentProjectileEntity
    ) {
        (
            (PersistentProjectileEntityInterface) persistentProjectileEntity
        ).setFrostLevel(
                EnchantmentHelper.getEquipmentLevel(
                    StrangeryEnchantments.FROST,
                    player
                )
            );
    }
}
