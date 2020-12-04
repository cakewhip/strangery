package com.kqp.strangery.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class StrangeryPickaxeItem extends PickaxeItem {
    public StrangeryPickaxeItem(ToolMaterial material) {
        super(
            material,
            1,
            -2.8F,
            new Item.Settings().group(ItemGroup.TOOLS)
        );
    }
}
