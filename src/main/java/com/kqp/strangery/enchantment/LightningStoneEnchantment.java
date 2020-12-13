package com.kqp.strangery.enchantment;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.FlintAndSteelItem;

public class LightningStoneEnchantment extends StrangeryEnchantment {

    public LightningStoneEnchantment() {
        super(
            Rarity.UNCOMMON,
            item -> item instanceof FlintAndSteelItem,
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
}
