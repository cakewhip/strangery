package com.kqp.strangery.enchantment;

import java.util.function.Predicate;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class StrangeryEnchantment extends Enchantment {

    public final Predicate<Item> itemTargetPredicate;

    protected StrangeryEnchantment(
        Rarity weight,
        Predicate<Item> itemTargetPredicate,
        EquipmentSlot[] slotTypes
    ) {
        super(weight, null, slotTypes);
        this.itemTargetPredicate = itemTargetPredicate;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return itemTargetPredicate.test(stack.getItem());
    }
}
