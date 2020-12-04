package com.kqp.strangery.item.tool;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

public class StrangeryShovelItem extends ShovelItem {
    public StrangeryShovelItem(ToolMaterial material) {
        super(
            material,
            1.5F,
            -3.0F,
            new Item.Settings().group(ItemGroup.TOOLS)
        );
    }
}
