package com.kqp.strangery.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class StrangerySwordItem extends SwordItem {
    public StrangerySwordItem(ToolMaterial material) {
        super(
            material,
            3,
            -2.4F,
            new Item.Settings().group(ItemGroup.COMBAT)
        );
    }
}
