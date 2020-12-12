package com.kqp.strangery.mixin;

import com.kqp.strangery.enchantment.WisdomEnchantment;
import com.kqp.strangery.init.StrangeryEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ExperienceOrbEntity.class)
public class WisdomEnchantmentMixin {

    @Redirect(
        method = "onPlayerCollision",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;addExperience(I)V"
        )
    )
    private void redirectAddExperience(PlayerEntity player, int experience) {
        int wisdomLevel = EnchantmentHelper.getEquipmentLevel(
            StrangeryEnchantments.WISDOM,
            player
        );

        boolean doubleExp =
            wisdomLevel > 0 &&
            player.getRandom().nextDouble() <
            ((double) wisdomLevel / WisdomEnchantment.MAX_LEVEL);

        player.addExperience(experience * (doubleExp ? 2 : 1));
    }
}
