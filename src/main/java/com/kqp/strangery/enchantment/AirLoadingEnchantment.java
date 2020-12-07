package com.kqp.strangery.enchantment;

import com.kqp.strangery.item.LongshotItem;
import net.minecraft.entity.EquipmentSlot;

public class AirLoadingEnchantment extends StrangeryEnchantment {

    public AirLoadingEnchantment() {
        super(
            Rarity.RARE,
            item -> item instanceof LongshotItem,
            new EquipmentSlot[] { EquipmentSlot.MAINHAND }
        );
    }

    @Override
    public int getMinPower(int level) {
        return level * 25;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 50;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
