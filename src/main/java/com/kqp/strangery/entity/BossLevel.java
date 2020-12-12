package com.kqp.strangery.entity;

import com.kqp.strangery.init.StrangeryItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Formatting;

public enum BossLevel {
    UNCOMMON(
        1,
        Formatting.WHITE,
        new Item[] {
            Items.IRON_PICKAXE,
            Items.IRON_AXE,
            Items.IRON_SWORD,
            Items.IRON_HELMET,
            Items.IRON_CHESTPLATE,
            Items.IRON_LEGGINGS,
            Items.IRON_BOOTS,
            Items.GOLDEN_PICKAXE,
            Items.GOLDEN_AXE,
            Items.GOLDEN_SWORD,
            Items.GOLDEN_HELMET,
            Items.GOLDEN_CHESTPLATE,
            Items.GOLDEN_LEGGINGS,
            Items.GOLDEN_BOOTS,
            Items.IRON_INGOT,
            Items.GOLD_INGOT,
            Items.COAL_BLOCK,
            Items.LAPIS_LAZULI,
            Items.GOLDEN_APPLE,
        }
    ),
    RARE(
        3,
        Formatting.YELLOW,
        new Item[] {
            Items.IRON_PICKAXE,
            Items.IRON_AXE,
            Items.IRON_SWORD,
            Items.IRON_HELMET,
            Items.IRON_CHESTPLATE,
            Items.IRON_LEGGINGS,
            Items.IRON_BOOTS,
            Items.GOLDEN_PICKAXE,
            Items.GOLDEN_AXE,
            Items.GOLDEN_SWORD,
            Items.GOLDEN_HELMET,
            Items.GOLDEN_CHESTPLATE,
            Items.GOLDEN_LEGGINGS,
            Items.GOLDEN_BOOTS,
            Items.IRON_INGOT,
            Items.GOLD_INGOT,
            Items.COAL_BLOCK,
            Items.LAPIS_LAZULI,
            Items.GOLDEN_APPLE,
        }
    ),
    EPIC(
        5,
        Formatting.AQUA,
        new Item[] {
            StrangeryItems.BEBSOFYR_PICKAXE,
            StrangeryItems.BEBSOFYR_AXE,
            StrangeryItems.BEBSOFYR_SWORD,
            StrangeryItems.BEBSOFYR_HELMET,
            StrangeryItems.BEBSOFYR_CHESTPLATE,
            StrangeryItems.BEBSOFYR_LEGGINGS,
            StrangeryItems.BEBSOFYR_BOOTS,
            Items.DIAMOND_PICKAXE,
            Items.DIAMOND_AXE,
            Items.DIAMOND_SWORD,
            Items.DIAMOND_HELMET,
            Items.DIAMOND_CHESTPLATE,
            Items.DIAMOND_LEGGINGS,
            Items.DIAMOND_BOOTS,
            Items.DIAMOND,
            Items.EMERALD,
            Items.IRON_BLOCK,
            Items.GOLD_BLOCK,
            Items.GOLDEN_APPLE,
            Items.ENCHANTED_GOLDEN_APPLE,
        }
    ),
    LEGENDARY(
        7,
        Formatting.LIGHT_PURPLE,
        new Item[] {
            StrangeryItems.BEBSOFYR_PICKAXE,
            StrangeryItems.BEBSOFYR_AXE,
            StrangeryItems.BEBSOFYR_SWORD,
            StrangeryItems.BEBSOFYR_HELMET,
            StrangeryItems.BEBSOFYR_CHESTPLATE,
            StrangeryItems.BEBSOFYR_LEGGINGS,
            StrangeryItems.BEBSOFYR_BOOTS,
            Items.DIAMOND_PICKAXE,
            Items.DIAMOND_AXE,
            Items.DIAMOND_SWORD,
            Items.DIAMOND_HELMET,
            Items.DIAMOND_CHESTPLATE,
            Items.DIAMOND_LEGGINGS,
            Items.DIAMOND_BOOTS,
            Items.DIAMOND,
            Items.EMERALD,
            Items.IRON_BLOCK,
            Items.GOLD_BLOCK,
            Items.GOLDEN_APPLE,
            Items.ENCHANTED_GOLDEN_APPLE,
        }
    ),
    MYTHICAL(
        9,
        Formatting.GREEN,
        new Item[] {
            Items.DIAMOND_PICKAXE,
            Items.DIAMOND_AXE,
            Items.DIAMOND_SWORD,
            Items.DIAMOND_HELMET,
            Items.DIAMOND_CHESTPLATE,
            Items.DIAMOND_LEGGINGS,
            Items.DIAMOND_BOOTS,
            Items.NETHERITE_PICKAXE,
            Items.NETHERITE_AXE,
            Items.NETHERITE_SWORD,
            Items.NETHERITE_HELMET,
            Items.NETHERITE_CHESTPLATE,
            Items.NETHERITE_LEGGINGS,
            Items.NETHERITE_BOOTS,
            Items.DIAMOND,
            Items.EMERALD,
            Items.LAPIS_BLOCK,
            Items.GOLD_BLOCK,
            Items.ENCHANTED_GOLDEN_APPLE,
        }
    ),
    GODLIKE(
        20,
        Formatting.RED,
        new Item[] {
            Items.NETHERITE_PICKAXE,
            Items.NETHERITE_AXE,
            Items.NETHERITE_SWORD,
            Items.NETHERITE_HELMET,
            Items.NETHERITE_CHESTPLATE,
            Items.NETHERITE_LEGGINGS,
            Items.NETHERITE_BOOTS,
            StrangeryItems.CELESTIAL_STEEL_PICKAXE,
            StrangeryItems.CELESTIAL_STEEL_AXE,
            StrangeryItems.CELESTIAL_STEEL_SWORD,
            StrangeryItems.CELESTIAL_STEEL_HELMET,
            StrangeryItems.CELESTIAL_STEEL_CHESTPLATE,
            StrangeryItems.CELESTIAL_STEEL_LEGGINGS,
            StrangeryItems.CELESTIAL_STEEL_BOOTS,
            Items.DIAMOND_BLOCK,
            Items.EMERALD_BLOCK,
            Items.NETHER_STAR,
        }
    );

    public int level;
    public Formatting formatting;
    public Item[] loot;

    BossLevel(int level, Formatting formatting, Item[] loot) {
        this.level = level;
        this.formatting = formatting;
        this.loot = loot;
    }
}
