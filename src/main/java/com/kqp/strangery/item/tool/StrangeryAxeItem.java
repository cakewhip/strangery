package com.kqp.strangery.item.tool;

import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterial;

public class StrangeryAxeItem extends AxeItem {

    public StrangeryAxeItem(ToolMaterial material) {
        super(
            material,
            6.0F,
            -3.0F,
            new Item.Settings().group(ItemGroup.TOOLS)
        );
    }
}
