package com.kqp.strangery.item.tool;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

public class StrangeryToolMaterial implements ToolMaterial {
    public static final ToolMaterial BEBSOFYR = tool(
        3, 3122, 7.75F, 2.75F, 10
    );

    public static final ToolMaterial CELESTIAL_STEEL = tool(
        5, 8124, 12.0F, 6.9F, 22
    );

    private final int miningLevel;
    private final int durability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    private StrangeryToolMaterial(int miningLevel, int durability, float miningSpeed,
                                  float attackDamage, int enchantability,
                                  Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public static StrangeryToolMaterial tool(int miningLevel, int durability, float miningSpeed,
                                             float attackDamage, int enchantability,
                                             Item... repairItems) {
        return new StrangeryToolMaterial(miningLevel, durability, miningSpeed, attackDamage,
            enchantability, () -> Ingredient.ofItems(repairItems));
    }
}
