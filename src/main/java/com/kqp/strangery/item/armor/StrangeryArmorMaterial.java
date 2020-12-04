package com.kqp.strangery.item.armor;

import com.kqp.strangery.Strangery;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.function.Supplier;

/**
 * Used to create custom armor materials.
 */
public class StrangeryArmorMaterial implements ArmorMaterial {
    public static final ArmorMaterial BEBSOFYR = new StrangeryArmorMaterial(
        "bebsofyr",
        31,
        new int[] {3, 6, 8, 3},
        10,
        () -> SoundEvents.ITEM_ARMOR_EQUIP_IRON,
        1.75F,
        0.0F,
        () -> Ingredient.ofItems(Strangery.I.BEBSOFYR_INGOT)
    );

    private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final Supplier<SoundEvent> equipSoundSupplier;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredientSupplier;

    private StrangeryArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts,
                                   int enchantability, Supplier<SoundEvent> equipSoundSupplier,
                                   float toughness, float knockbackResistance,
                                   Supplier<Ingredient> ingredientSupplier) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSoundSupplier = equipSoundSupplier;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredientSupplier = ingredientSupplier;
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * this.durabilityMultiplier;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return this.protectionAmounts[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSoundSupplier.get();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredientSupplier.get();
    }

    @Environment(EnvType.CLIENT)
    public String getName() {
        return this.name;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }
}