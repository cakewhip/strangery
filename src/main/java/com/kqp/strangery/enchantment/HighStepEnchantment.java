package com.kqp.strangery.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;

public class HighStepEnchantment extends StrangeryEnchantment {

    public HighStepEnchantment() {
        super(
            Rarity.RARE,
            item ->
                item instanceof ArmorItem &&
                ((ArmorItem) item).getSlotType() == EquipmentSlot.FEET,
            new EquipmentSlot[] { EquipmentSlot.FEET }
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
        return (
            super.canAccept(other) &&
            other != Enchantments.DEPTH_STRIDER &&
            other != Enchantments.FROST_WALKER
        );
    }
}
