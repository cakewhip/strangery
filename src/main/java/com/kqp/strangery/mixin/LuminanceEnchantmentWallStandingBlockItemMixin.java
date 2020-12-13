package com.kqp.strangery.mixin;

import com.kqp.strangery.init.StrangeryBlocks;
import com.kqp.strangery.init.StrangeryEnchantments;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.WallStandingBlockItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WallStandingBlockItem.class)
public class LuminanceEnchantmentWallStandingBlockItemMixin {

    @Redirect(
        method = "getPlacementState",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/Block;getPlacementState(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/block/BlockState;"
        )
    )
    private BlockState redirectGetPlacementState(
        Block block,
        ItemPlacementContext context,
        ItemPlacementContext _context
    ) {
        int luminanceLevel = EnchantmentHelper.getLevel(
            StrangeryEnchantments.LUMINANCE,
            context.getStack()
        );

        if (luminanceLevel > 0) {
            if (block == Blocks.TORCH) {
                return StrangeryBlocks.LUMINANCE_TORCH.getPlacementState(
                    context
                );
            } else if (block == Blocks.WALL_TORCH) {
                return StrangeryBlocks.WALL_LUMINANCE_TORCH.getPlacementState(
                    context
                );
            }
        }

        return block.getPlacementState(context);
    }
}
