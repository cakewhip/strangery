package com.kqp.strangery.item.armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class StrangeryArmorItem extends ArmorItem {

  public StrangeryArmorItem(ArmorMaterial material, EquipmentSlot slot) {
    super(material, slot, new Item.Settings().group(ItemGroup.COMBAT));
  }
}
