package com.kqp.strangery.enchantment;

import net.minecraft.enchantment.AquaAffinityEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.RespirationEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;

public class WisdomEnchantment extends StrangeryEnchantment {

    public static int MAX_LEVEL = 3;

    public WisdomEnchantment() {
        super(
            Rarity.RARE,
            item ->
                item instanceof ArmorItem &&
                ((ArmorItem) item).getSlotType() == EquipmentSlot.HEAD,
            new EquipmentSlot[] { EquipmentSlot.HEAD }
        );
    }

    @Override
    public int getMinPower(int level) {
        return level * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 15;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return (
                other instanceof RespirationEnchantment ||
                other instanceof AquaAffinityEnchantment
            )
            ? false
            : super.canAccept(other);
    }
}
