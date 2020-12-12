package com.kqp.strangery.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.SwordItem;

public class FrozenEdgeEnchantment extends StrangeryEnchantment {

    public FrozenEdgeEnchantment() {
        super(
            Rarity.RARE,
            item -> item instanceof SwordItem,
            new EquipmentSlot[] { EquipmentSlot.MAINHAND }
        );
    }

    @Override
    public int getMinPower(int level) {
        return 10 + 20 * (level - 1);
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return other instanceof FireAspectEnchantment
            ? false
            : super.canAccept(other);
    }
}
