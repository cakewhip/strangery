package com.kqp.strangery.mixin.accessor;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Used to add accessors to the AbstractBlock class.
 */
@Mixin(AbstractBlock.class)
public interface BlockAccessor {
    @Accessor
    Material getMaterial();
}
