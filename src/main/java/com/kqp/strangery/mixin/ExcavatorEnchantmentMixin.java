package com.kqp.strangery.mixin;

import com.google.common.collect.Sets;
import com.kqp.strangery.init.StrangeryEnchantments;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ExcavatorEnchantmentMixin {

    private static final Set<Block> SHOVEL_EFFECTIVE_BLOCKS;

    @Inject(method = "isEffectiveOn", at = @At("HEAD"), cancellable = true)
    private void injectIsEffectiveOn(
        BlockState blockState,
        CallbackInfoReturnable<Boolean> callbackInfo
    ) {
        ItemStack itemStack = (ItemStack) (Object) this;

        if (itemStack.getItem() instanceof PickaxeItem) {
            int excavatorLevel = EnchantmentHelper.getLevel(
                StrangeryEnchantments.EXCAVATOR,
                itemStack
            );

            if (excavatorLevel > 0) {
                if (SHOVEL_EFFECTIVE_BLOCKS.contains(blockState.getBlock())) {
                    callbackInfo.setReturnValue(true);
                }
            }
        }
    }

    @Inject(
        method = "getMiningSpeedMultiplier",
        at = @At("HEAD"),
        cancellable = true
    )
    private void injectGetMiningSpeedMultiplier(
        BlockState blockState,
        CallbackInfoReturnable<Float> callbackInfo
    ) {
        ItemStack itemStack = (ItemStack) (Object) this;

        if (itemStack.getItem() instanceof PickaxeItem) {
            int excavatorLevel = EnchantmentHelper.getLevel(
                StrangeryEnchantments.EXCAVATOR,
                itemStack
            );

            if (excavatorLevel > 0) {
                if (SHOVEL_EFFECTIVE_BLOCKS.contains(blockState.getBlock())) {
                    callbackInfo.setReturnValue(
                        itemStack
                            .getItem()
                            .getMiningSpeedMultiplier(
                                itemStack,
                                Blocks.STONE.getDefaultState()
                            )
                    );
                }
            }
        }
    }

    static {
        SHOVEL_EFFECTIVE_BLOCKS =
            Sets.newHashSet(
                Blocks.CLAY,
                Blocks.DIRT,
                Blocks.COARSE_DIRT,
                Blocks.PODZOL,
                Blocks.FARMLAND,
                Blocks.GRASS_BLOCK,
                Blocks.GRAVEL,
                Blocks.MYCELIUM,
                Blocks.SAND,
                Blocks.RED_SAND,
                Blocks.SNOW_BLOCK,
                Blocks.SNOW,
                Blocks.SOUL_SAND,
                Blocks.GRASS_PATH,
                Blocks.WHITE_CONCRETE_POWDER,
                Blocks.ORANGE_CONCRETE_POWDER,
                Blocks.MAGENTA_CONCRETE_POWDER,
                Blocks.LIGHT_BLUE_CONCRETE_POWDER,
                Blocks.YELLOW_CONCRETE_POWDER,
                Blocks.LIME_CONCRETE_POWDER,
                Blocks.PINK_CONCRETE_POWDER,
                Blocks.GRAY_CONCRETE_POWDER,
                Blocks.LIGHT_GRAY_CONCRETE_POWDER,
                Blocks.CYAN_CONCRETE_POWDER,
                Blocks.PURPLE_CONCRETE_POWDER,
                Blocks.BLUE_CONCRETE_POWDER,
                Blocks.BROWN_CONCRETE_POWDER,
                Blocks.GREEN_CONCRETE_POWDER,
                Blocks.RED_CONCRETE_POWDER,
                Blocks.BLACK_CONCRETE_POWDER,
                Blocks.SOUL_SOIL
            );
    }
}
