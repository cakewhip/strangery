package com.kqp.strangery.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.FlameEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BowItem;

public class FrostEnchantment extends StrangeryEnchantment {

    public FrostEnchantment() {
        super(
            Rarity.RARE,
            item -> item instanceof BowItem,
            new EquipmentSlot[] { EquipmentSlot.MAINHAND }
        );
    }

    @Override
    public int getMinPower(int level) {
        return 20;
    }

    @Override
    public int getMaxPower(int level) {
        return 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return other instanceof FlameEnchantment
            ? false
            : super.canAccept(other);
    }
}
