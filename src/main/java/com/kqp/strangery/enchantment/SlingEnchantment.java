package com.kqp.strangery.enchantment;

import com.kqp.strangery.item.LongshotItem;
import net.minecraft.entity.EquipmentSlot;

public class SlingEnchantment extends StrangeryEnchantment {

    public SlingEnchantment() {
        super(
            Rarity.UNCOMMON,
            item -> item instanceof LongshotItem,
            new EquipmentSlot[] { EquipmentSlot.MAINHAND }
        );
    }

    @Override
    public int getMinPower(int level) {
        return 1 + (level - 1) * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 15;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
