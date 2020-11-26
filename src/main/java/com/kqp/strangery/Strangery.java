package com.kqp.strangery;

import com.kqp.strangery.statuseffect.CustomStatusEffect;
import com.kqp.strangery.statuseffect.HallucinatingStatusEffect;
import com.kqp.strangery.statuseffect.HealthStatusEffect;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.HashMap;
import java.util.Map;

public class Strangery implements ModInitializer {
    public static final String MOD_ID = "strangery";

    @Override
    public void onInitialize() {
        B.init();
        I.init();
        SE.init();

        FDO.init();

        WF.init();
    }

    public static Identifier id(String name) {
        return new Identifier(MOD_ID, name);
    }

    // Blocks
    public static class B {
        public static final Block FOODIUM_ORE =
            register(new Block(FabricBlockSettings
                .of(Material.STONE)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES, 2)
                .strength(3.0F, 3.0F)
            ), "foodium_ore");

        public static void init() {
        }

        private static Block register(Block block, String name) {
            Registry.register(Registry.BLOCK, id(name), block);
            Registry.register(
                Registry.ITEM,
                id(name),
                new BlockItem(block, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS))
            );

            return block;
        }
    }

    // Items
    public static class I {
        public static final Item UNWIELDY_STICK =
            register(new Item(new Item.Settings().group(ItemGroup.MATERIALS)), "unwieldy_stick");

        public static final Item FOODIUM =
            register(new Item(new Item.Settings().group(ItemGroup.MATERIALS)), "foodium");

        public static final Item BUN = register(new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(foodComp(2, 0.1F))
        ), "bun");

        public static final Item CHEESE = register(new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(foodComp(1, 0.0F))
        ), "cheese");

        public static final Item RICE = register(new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(foodComp(2, 0.1F))
        ), "rice");

        public static final Item FRENCH_FRIES = register(new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(foodComp(4, 0.1F))
        ), "french_fries");

        public static final Item CHEESE_BURGER = register(new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(foodComp(12, 0.3F))
        ), "cheese_burger");

        public static final Item ICE_CREAM = register(new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(foodComp(6, 0.1F))
        ), "ice_cream");

        public static final Item CHICKEN_FRIED_RICE = register(new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(foodComp(12, 0.3F))
        ), "chicken_fried_rice");

        public static final Item CHICKEN_TENDIES = register(new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(foodComp(10, 0.2F))
        ), "chicken_tendies");

        public static final Item KOREAN_BBQ = register(new Item(new Item.Settings()

            .group(ItemGroup.FOOD)
            .food(foodComp(18, 0.5F))
        ), "korean_bbq");

        public static final Item SUSHI = register(new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(foodComp(18, 0.4F))
        ), "sushi");

        public static final Item PHO = register(new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(foodComp(16, 0.4F))
        ), "pho");

        public static final Item RAMEN = register(new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(foodComp(16, 0.25F))
        ), "ramen");

        public static final Item PIZZA = register(new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(foodComp(18, 0.25F))
        ), "pizza");

        public static final Item ROCK_CANDY = register(new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(new FoodComponent.Builder()
                .hunger(4)
                .saturationModifier(0.05F)
                .snack()
                .statusEffect(new StatusEffectInstance(
                    StatusEffects.HASTE, 60 * 20, 0
                ), 1.0F)
                .statusEffect(new StatusEffectInstance(
                    StatusEffects.SPEED, 30 * 20, 0
                ), 1.0F)
                .build()
            )
        ), "rock_candy");

        public static void init() {
        }

        private static Item register(Item item, String name) {
            Registry.register(Registry.ITEM, id(name), item);

            return item;
        }

        private static FoodComponent foodComp(int hunger, float saturation) {
            return new FoodComponent.Builder()
                .hunger(hunger)
                .saturationModifier(saturation)
                .build();
        }
    }

    // Status effects
    public static class SE {
        public static final StatusEffect DEAF = register(new CustomStatusEffect(
            StatusEffectType.HARMFUL,
            0xFFFFFF
        ), "deaf");
        public static final StatusEffect BLEEDING = register(new HealthStatusEffect(
            StatusEffectType.HARMFUL,
            0xFFFFFF,
            DamageSource.GENERIC,
            2,
            2,
            15
        ), "bleeding");
        public static final StatusEffect FROSTBITE = register(new HealthStatusEffect(
            StatusEffectType.HARMFUL,
            0xFFFFFF,
            DamageSource.GENERIC,
            2,
            2,
            25
        ), "frostbite");
        public static final StatusEffect CONFUSION = register(new CustomStatusEffect(
            StatusEffectType.HARMFUL,
            0xFFFFFF
        ), "confusion");
        public static final StatusEffect HALLUCINATING = register(new HallucinatingStatusEffect(
            StatusEffectType.HARMFUL,
            0xFFFFFF
        ), "hallucinating");

        public static void init() {
        }

        private static StatusEffect register(StatusEffect statusEffect, String name) {
            Registry.register(Registry.STATUS_EFFECT, id(name), statusEffect);

            return statusEffect;
        }
    }

    // Food data overrides
    public static class FDO {
        private static final Map<Identifier, FoodComponent>
            ITEM_FOOD_COMPONENT_MAP = new HashMap<Identifier, FoodComponent>();

        private static final FoodComponent FAUNA = (new FoodComponent.Builder())
            .hunger(1)
            .saturationModifier(0.1F)
            .statusEffect(new StatusEffectInstance(
                StatusEffects.WEAKNESS, 30 * 20), 0.75F)
            .statusEffect(new StatusEffectInstance(
                StatusEffects.SLOWNESS, 30 * 20), 0.5F)
            .snack()
            .build();

        private static final FoodComponent MUSHROOM = (new FoodComponent.Builder())
            .hunger(2)
            .saturationModifier(0.3F)
            .statusEffect(new StatusEffectInstance(
                Strangery.SE.CONFUSION, 30 * 20), 0.75F)
            .statusEffect(new StatusEffectInstance(
                Strangery.SE.HALLUCINATING, 30 * 20), 0.75F)
            .statusEffect(new StatusEffectInstance(
                StatusEffects.WEAKNESS, 30 * 20), 0.75F)
            .statusEffect(new StatusEffectInstance(
                StatusEffects.POISON, 30 * 20), 0.75F)
            .statusEffect(new StatusEffectInstance(
                StatusEffects.BLINDNESS, 30 * 20), 0.75F)
            .snack()
            .build();

        private static final FoodComponent SEDIMENT = (new FoodComponent.Builder())
            .hunger(1)
            .saturationModifier(0.01F)
            .statusEffect(new StatusEffectInstance(
                StatusEffects.SLOWNESS, 30 * 20), 0.75F)
            .statusEffect(new StatusEffectInstance(
                StatusEffects.HUNGER, 30 * 20), 0.5F)
            .statusEffect(new StatusEffectInstance(
                Strangery.SE.BLEEDING, 30 * 20), 0.25F)
            .statusEffect(new StatusEffectInstance(
                StatusEffects.BLINDNESS, 30 * 20), 0.1F)
            .build();

        private static final FoodComponent CACTUS = (new FoodComponent.Builder())
            .hunger(4)
            .saturationModifier(0.04F)
            .statusEffect(new StatusEffectInstance(
                Strangery.SE.BLEEDING, 15 * 20), 1.0F)
            .snack()
            .build();

        private static final FoodComponent GLOWSTONE_DUST = (new FoodComponent.Builder())
            .hunger(1)
            .saturationModifier(0.01F)
            .statusEffect(new StatusEffectInstance(
                StatusEffects.GLOWING, 4 * 20), 0.5F)
            .statusEffect(new StatusEffectInstance(
                Strangery.SE.BLEEDING, 4 * 20), 0.5F)
            .snack()
            .build();

        public static void init() {
            add(Items.DANDELION, FAUNA);
            add(Items.POPPY, FAUNA);
            add(Items.BLUE_ORCHID, FAUNA);
            add(Items.ALLIUM, FAUNA);
            add(Items.AZURE_BLUET, FAUNA);
            add(Items.RED_TULIP, FAUNA);
            add(Items.ORANGE_TULIP, FAUNA);
            add(Items.WHITE_TULIP, FAUNA);
            add(Items.PINK_TULIP, FAUNA);
            add(Items.OXEYE_DAISY, FAUNA);
            add(Items.CORNFLOWER, FAUNA);
            add(Items.LILY_OF_THE_VALLEY, FAUNA);
            add(Items.WITHER_ROSE, FAUNA);

            add(Items.ACACIA_LEAVES, FAUNA);
            add(Items.BIRCH_LEAVES, FAUNA);
            add(Items.DARK_OAK_LEAVES, FAUNA);
            add(Items.JUNGLE_LEAVES, FAUNA);
            add(Items.OAK_LEAVES, FAUNA);
            add(Items.SPRUCE_LEAVES, FAUNA);

            add(Items.SPRUCE_SAPLING, FAUNA);
            add(Items.ACACIA_SAPLING, FAUNA);
            add(Items.BIRCH_SAPLING, FAUNA);
            add(Items.DARK_OAK_SAPLING, FAUNA);
            add(Items.JUNGLE_SAPLING, FAUNA);
            add(Items.OAK_SAPLING, FAUNA);

            add(Items.GRASS, FAUNA);
            add(Items.TALL_GRASS, FAUNA);
            add(Items.DEAD_BUSH, FAUNA);
            add(Items.VINE, FAUNA);
            add(Items.LILY_PAD, FAUNA);

            add(Items.BEETROOT_SEEDS, FAUNA);
            add(Items.MELON_SEEDS, FAUNA);
            add(Items.PUMPKIN_SEEDS, FAUNA);
            add(Items.WHEAT_SEEDS, FAUNA);

            add(Items.BROWN_MUSHROOM, MUSHROOM);
            add(Items.BROWN_MUSHROOM_BLOCK, MUSHROOM);
            add(Items.RED_MUSHROOM, MUSHROOM);
            add(Items.RED_MUSHROOM_BLOCK, MUSHROOM);
            add(Items.MUSHROOM_STEM, MUSHROOM);
            add(Items.MYCELIUM, MUSHROOM);

            add(Items.GRASS_BLOCK, SEDIMENT);
            add(Items.PODZOL, SEDIMENT);
            add(Items.GRASS_PATH, SEDIMENT);
            add(Items.DIRT, SEDIMENT);
            add(Items.COARSE_DIRT, SEDIMENT);
            add(Items.SAND, SEDIMENT);
            add(Items.RED_SAND, SEDIMENT);
            add(Items.GRAVEL, SEDIMENT);
            add(Items.CLAY, SEDIMENT);
            add(Items.CLAY_BALL, SEDIMENT);

            add(Items.CACTUS, CACTUS);

            add(Items.GLOWSTONE_DUST, GLOWSTONE_DUST);
        }

        public static FoodComponent get(Item item) {
            return ITEM_FOOD_COMPONENT_MAP.getOrDefault(
                Registry.ITEM.getId(item),
                null
            );
        }

        private static void add(Item item, FoodComponent foodComponent) {
            ITEM_FOOD_COMPONENT_MAP.put(Registry.ITEM.getId(item), foodComponent);
        }
    }

    public static class WF {
        private static final ConfiguredFeature<?, ?> FOODIUM_ORE_OVERWORLD = Feature.ORE
            .configure(new OreFeatureConfig(
                OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                B.FOODIUM_ORE.getDefaultState(),
                8
            ))
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(
                0,
                0,
                64
            )))
            .spreadHorizontally()
            .repeat(12);

        public static void init() {
            Registry.register(
                BuiltinRegistries.CONFIGURED_FEATURE,
                id("foodium_ore"),
                FOODIUM_ORE_OVERWORLD
            );
            BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                BuiltinRegistries.CONFIGURED_FEATURE.getKey(FOODIUM_ORE_OVERWORLD).get()
            );
        }
    }
}
