package com.kqp.strangery.mixin;

import com.kqp.strangery.init.StrangeryEnchantments;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelEnchantmentsMixin {

    private static final Random RANDOM = new Random();

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void injectUseOnBlock(
        ItemUsageContext context,
        CallbackInfoReturnable<ActionResult> callbackInfo
    ) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        ItemStack itemStack = context.getStack();

        int lightningStoneLevel = EnchantmentHelper.getEquipmentLevel(
            StrangeryEnchantments.LIGHTNING_STONE,
            player
        );
        int waterStoneLevel = EnchantmentHelper.getEquipmentLevel(
            StrangeryEnchantments.WATER_STONE,
            player
        );

        if (lightningStoneLevel > 0 || waterStoneLevel > 0) {
            BlockPos targetBlockPos = blockPos.offset(context.getSide());

            if (lightningStoneLevel > 0) {
                LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(
                    world
                );
                lightningEntity.refreshPositionAfterTeleport(
                    targetBlockPos.getX(),
                    targetBlockPos.getY(),
                    targetBlockPos.getZ()
                );
                world.spawnEntity(lightningEntity);
            }

            if (waterStoneLevel > 0) {
                world.playSound(
                    player,
                    targetBlockPos,
                    SoundEvents.ITEM_FLINTANDSTEEL_USE,
                    SoundCategory.BLOCKS,
                    1.0F,
                    RANDOM.nextFloat() * 0.4F + 0.8F
                );

                BlockState targetBlockState = Blocks.WATER.getDefaultState();

                world.setBlockState(targetBlockPos, targetBlockState, 11);
                if (player instanceof ServerPlayerEntity) {
                    Criteria.PLACED_BLOCK.trigger(
                        (ServerPlayerEntity) player,
                        targetBlockPos,
                        itemStack
                    );
                }
            }

            itemStack.damage(
                1,
                player,
                (p -> p.sendToolBreakStatus(context.getHand()))
            );

            player.getItemCooldownManager().set(itemStack.getItem(), 30 * 20);

            callbackInfo.setReturnValue(ActionResult.success(world.isClient));
        }
    }
}
