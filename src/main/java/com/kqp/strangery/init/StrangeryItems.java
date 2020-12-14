package com.kqp.strangery.init;

import com.kqp.strangery.item.LongshotItem;
import com.kqp.strangery.item.armor.StrangeryArmorItem;
import com.kqp.strangery.item.armor.StrangeryArmorMaterial;
import com.kqp.strangery.item.tool.StrangeryAxeItem;
import com.kqp.strangery.item.tool.StrangeryHoeItem;
import com.kqp.strangery.item.tool.StrangeryPickaxeItem;
import com.kqp.strangery.item.tool.StrangeryShovelItem;
import com.kqp.strangery.item.tool.StrangerySwordItem;
import com.kqp.strangery.item.tool.StrangeryToolMaterial;
import com.kqp.strangery.item.trinket.SansBoneItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class StrangeryItems {

    public static final Item UNWIELDY_STICK = register(
        new Item(new Item.Settings().group(ItemGroup.MATERIALS)),
        "unwieldy_stick"
    );

    public static final Item FOODIUM = register(
        new Item(new Item.Settings().group(ItemGroup.MATERIALS)),
        "foodium"
    );

    public static final Item BUN = register(
        new Item(
            new Item.Settings().group(ItemGroup.FOOD).food(foodComp(7, 0.6F))
        ),
        "bun"
    );

    public static final Item CHEESE = register(
        new Item(
            new Item.Settings().group(ItemGroup.FOOD).food(foodComp(2, 0.2F))
        ),
        "cheese"
    );

    public static final Item RICE = register(
        new Item(
            new Item.Settings().group(ItemGroup.FOOD).food(foodComp(7, 0.6F))
        ),
        "rice"
    );

    public static final Item FRENCH_FRIES = register(
        new Item(
            new Item.Settings().group(ItemGroup.FOOD).food(foodComp(10, 0.5F))
        ),
        "french_fries"
    );

    public static final Item CHEESE_BURGER = register(
        new Item(
            new Item.Settings().group(ItemGroup.FOOD).food(foodComp(20, 0.8F))
        ),
        "cheese_burger"
    );

    public static final Item ICE_CREAM = register(
        new Item(
            new Item.Settings()
                .group(ItemGroup.FOOD)
                .food(
                    new FoodComponent.Builder()
                        .hunger(6)
                        .saturationModifier(0.3F)
                        .statusEffect(
                            new StatusEffectInstance(
                                StatusEffects.SPEED,
                                30 * 20,
                                0
                            ),
                            1.0F
                        )
                        .alwaysEdible()
                        .build()
                )
        ),
        "ice_cream"
    );

    public static final Item CHICKEN_FRIED_RICE = register(
        new Item(
            new Item.Settings().group(ItemGroup.FOOD).food(foodComp(14, 0.7F))
        ),
        "chicken_fried_rice"
    );

    public static final Item CHICKEN_TENDIES = register(
        new Item(
            new Item.Settings().group(ItemGroup.FOOD).food(foodComp(10, 0.7F))
        ),
        "chicken_tendies"
    );

    public static final Item KOREAN_BBQ = register(
        new Item(
            new Item.Settings().group(ItemGroup.FOOD).food(foodComp(20, 1.0F))
        ),
        "korean_bbq"
    );

    public static final Item SUSHI = register(
        new Item(
            new Item.Settings().group(ItemGroup.FOOD).food(foodComp(20, 0.9F))
        ),
        "sushi"
    );

    public static final Item PHO = register(
        new Item(
            new Item.Settings().group(ItemGroup.FOOD).food(foodComp(16, 0.8F))
        ),
        "pho"
    );

    public static final Item RAMEN = register(
        new Item(
            new Item.Settings().group(ItemGroup.FOOD).food(foodComp(14, 0.7F))
        ),
        "ramen"
    );

    public static final Item PIZZA = register(
        new Item(
            new Item.Settings().group(ItemGroup.FOOD).food(foodComp(14, 0.6F))
        ),
        "pizza"
    );

    public static final Item ROCK_CANDY = register(
        new Item(
            new Item.Settings()
                .group(ItemGroup.FOOD)
                .food(
                    new FoodComponent.Builder()
                        .hunger(2)
                        .saturationModifier(0.0F)
                        .snack()
                        .statusEffect(
                            new StatusEffectInstance(
                                StatusEffects.HASTE,
                                60 * 20,
                                0
                            ),
                            1.0F
                        )
                        .statusEffect(
                            new StatusEffectInstance(
                                StatusEffects.SPEED,
                                30 * 20,
                                0
                            ),
                            1.0F
                        )
                        .alwaysEdible()
                        .build()
                )
        ),
        "rock_candy"
    );

    public static final Item BEBSOFYR_INGOT = register(
        new Item(new Item.Settings().group(ItemGroup.MATERIALS)),
        "bebsofyr_ingot"
    );

    public static final Item BEBSOFYR_PICKAXE = register(
        new StrangeryPickaxeItem(StrangeryToolMaterial.BEBSOFYR),
        "bebsofyr_pickaxe"
    );
    public static final Item BEBSOFYR_SHOVEL = register(
        new StrangeryShovelItem(StrangeryToolMaterial.BEBSOFYR),
        "bebsofyr_shovel"
    );
    public static final Item BEBSOFYR_AXE = register(
        new StrangeryAxeItem(StrangeryToolMaterial.BEBSOFYR),
        "bebsofyr_axe"
    );
    public static final Item BEBSOFYR_HOE = register(
        new StrangeryHoeItem(StrangeryToolMaterial.BEBSOFYR),
        "bebsofyr_hoe"
    );
    public static final Item BEBSOFYR_SWORD = register(
        new StrangerySwordItem(StrangeryToolMaterial.BEBSOFYR),
        "bebsofyr_sword"
    );

    public static final Item BEBSOFYR_HELMET = register(
        new StrangeryArmorItem(
            StrangeryArmorMaterial.BEBSOFYR,
            EquipmentSlot.HEAD
        ),
        "bebsofyr_helmet"
    );
    public static final Item BEBSOFYR_CHESTPLATE = register(
        new StrangeryArmorItem(
            StrangeryArmorMaterial.BEBSOFYR,
            EquipmentSlot.CHEST
        ),
        "bebsofyr_chestplate"
    );
    public static final Item BEBSOFYR_LEGGINGS = register(
        new StrangeryArmorItem(
            StrangeryArmorMaterial.BEBSOFYR,
            EquipmentSlot.LEGS
        ),
        "bebsofyr_leggings"
    );
    public static final Item BEBSOFYR_BOOTS = register(
        new StrangeryArmorItem(
            StrangeryArmorMaterial.BEBSOFYR,
            EquipmentSlot.FEET
        ),
        "bebsofyr_boots"
    );

    public static final Item MOONSTONE_FRAGMENT = register(
        new Item(new Item.Settings().group(ItemGroup.MATERIALS)),
        "moonstone_fragment"
    );
    public static final Item SUNSTONE_FRAGMENT = register(
        new Item(new Item.Settings().group(ItemGroup.MATERIALS)),
        "sunstone_fragment"
    );
    public static final Item CELESTIAL_STEEL_INGOT = register(
        new Item(new Item.Settings().group(ItemGroup.MATERIALS)),
        "celestial_steel_ingot"
    );

    public static final Item CELESTIAL_STEEL_PICKAXE = register(
        new StrangeryPickaxeItem(StrangeryToolMaterial.CELESTIAL_STEEL),
        "celestial_steel_pickaxe"
    );
    public static final Item CELESTIAL_STEEL_SHOVEL = register(
        new StrangeryShovelItem(StrangeryToolMaterial.CELESTIAL_STEEL),
        "celestial_steel_shovel"
    );
    public static final Item CELESTIAL_STEEL_AXE = register(
        new StrangeryAxeItem(StrangeryToolMaterial.CELESTIAL_STEEL),
        "celestial_steel_axe"
    );
    public static final Item CELESTIAL_STEEL_HOE = register(
        new StrangeryHoeItem(StrangeryToolMaterial.CELESTIAL_STEEL),
        "celestial_steel_hoe"
    );
    public static final Item CELESTIAL_STEEL_SWORD = register(
        new StrangerySwordItem(StrangeryToolMaterial.CELESTIAL_STEEL),
        "celestial_steel_sword"
    );

    public static final Item CELESTIAL_STEEL_HELMET = register(
        new StrangeryArmorItem(
            StrangeryArmorMaterial.CELESTIAL_STEEL,
            EquipmentSlot.HEAD
        ),
        "celestial_steel_helmet"
    );
    public static final Item CELESTIAL_STEEL_CHESTPLATE = register(
        new StrangeryArmorItem(
            StrangeryArmorMaterial.CELESTIAL_STEEL,
            EquipmentSlot.CHEST
        ),
        "celestial_steel_chestplate"
    );
    public static final Item CELESTIAL_STEEL_LEGGINGS = register(
        new StrangeryArmorItem(
            StrangeryArmorMaterial.CELESTIAL_STEEL,
            EquipmentSlot.LEGS
        ),
        "celestial_steel_leggings"
    );
    public static final Item CELESTIAL_STEEL_BOOTS = register(
        new StrangeryArmorItem(
            StrangeryArmorMaterial.CELESTIAL_STEEL,
            EquipmentSlot.FEET
        ),
        "celestial_steel_boots"
    );

    public static final Item LONGSHOT = register(
        new LongshotItem(),
        "longshot"
    );

    public static final Item FEATHER_SUIT_HELMET = register(
        new StrangeryArmorItem(
            StrangeryArmorMaterial.FEATHER_SUIT,
            EquipmentSlot.HEAD
        ),
        "feather_suit_helmet"
    );
    public static final Item FEATHER_SUIT_CHESTPLATE = register(
        new StrangeryArmorItem(
            StrangeryArmorMaterial.FEATHER_SUIT,
            EquipmentSlot.CHEST
        ),
        "feather_suit_chestplate"
    );
    public static final Item FEATHER_SUIT_LEGGINGS = register(
        new StrangeryArmorItem(
            StrangeryArmorMaterial.FEATHER_SUIT,
            EquipmentSlot.LEGS
        ),
        "feather_suit_leggings"
    );
    public static final Item FEATHER_SUIT_BOOTS = register(
        new StrangeryArmorItem(
            StrangeryArmorMaterial.FEATHER_SUIT,
            EquipmentSlot.FEET
        ),
        "feather_suit_boots"
    );

    public static final Item SANS_BONE = register(
        new SansBoneItem(
            new Item.Settings().group(ItemGroup.TOOLS).maxCount(1)
        ),
        "sans_bone"
    );

    public static void init() {}

    private static Item register(Item item, String name) {
        Registry.register(Registry.ITEM, Strangery.id(name), item);

        return item;
    }

    private static FoodComponent foodComp(int hunger, float saturation) {
        return new FoodComponent.Builder()
            .hunger(hunger)
            .saturationModifier(saturation)
            .build();
    }
}
