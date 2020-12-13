package com.kqp.strangery.enchantment;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.PickaxeItem;

public class ExcavatorEnchantment extends StrangeryEnchantment {

    public ExcavatorEnchantment() {
        super(
            Rarity.RARE,
            item -> item instanceof PickaxeItem,
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
