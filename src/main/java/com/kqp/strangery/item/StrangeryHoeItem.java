package com.kqp.strangery.item;

import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterial;

public class StrangeryHoeItem extends HoeItem {
    public StrangeryHoeItem(ToolMaterial material) {
        super(
            material,
            -3,
            0.0F,
            new Item.Settings().group(ItemGroup.TOOLS)
        );
    }
}
