package com.kqp.strangery.init.data;

import com.kqp.strangery.init.StrangeryStatusEffects;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FoodDataOverrides {

    private static final Map<Identifier, FoodComponent> ITEM_FOOD_COMPONENT_MAP = new HashMap<Identifier, FoodComponent>();

    private static final FoodComponent FAUNA =
        (new FoodComponent.Builder()).hunger(1)
            .saturationModifier(0.1F)
            .statusEffect(
                new StatusEffectInstance(StatusEffects.WEAKNESS, 30 * 20),
                0.75F
            )
            .statusEffect(
                new StatusEffectInstance(StatusEffects.SLOWNESS, 30 * 20),
                0.5F
            )
            .snack()
            .build();

    private static final FoodComponent MUSHROOM =
        (new FoodComponent.Builder()).hunger(2)
            .saturationModifier(0.3F)
            .statusEffect(
                new StatusEffectInstance(
                    StrangeryStatusEffects.CONFUSION,
                    30 * 20
                ),
                0.75F
            )
            .statusEffect(
                new StatusEffectInstance(
                    StrangeryStatusEffects.HALLUCINATING,
                    30 * 20
                ),
                0.75F
            )
            .statusEffect(
                new StatusEffectInstance(StatusEffects.WEAKNESS, 30 * 20),
                0.75F
            )
            .statusEffect(
                new StatusEffectInstance(StatusEffects.POISON, 30 * 20),
                0.75F
            )
            .statusEffect(
                new StatusEffectInstance(StatusEffects.BLINDNESS, 30 * 20),
                0.75F
            )
            .snack()
            .build();

    private static final FoodComponent SEDIMENT =
        (new FoodComponent.Builder()).hunger(1)
            .saturationModifier(0.01F)
            .statusEffect(
                new StatusEffectInstance(StatusEffects.SLOWNESS, 30 * 20),
                0.75F
            )
            .statusEffect(
                new StatusEffectInstance(StatusEffects.HUNGER, 30 * 20),
                0.5F
            )
            .statusEffect(
                new StatusEffectInstance(
                    StrangeryStatusEffects.BLEEDING,
                    30 * 20
                ),
                0.25F
            )
            .statusEffect(
                new StatusEffectInstance(StatusEffects.BLINDNESS, 30 * 20),
                0.1F
            )
            .build();

    private static final FoodComponent CACTUS =
        (new FoodComponent.Builder()).hunger(4)
            .saturationModifier(0.04F)
            .statusEffect(
                new StatusEffectInstance(
                    StrangeryStatusEffects.BLEEDING,
                    15 * 20
                ),
                1.0F
            )
            .snack()
            .build();

    private static final FoodComponent GLOWSTONE_DUST =
        (new FoodComponent.Builder()).hunger(1)
            .saturationModifier(0.01F)
            .statusEffect(
                new StatusEffectInstance(StatusEffects.GLOWING, 4 * 20),
                0.5F
            )
            .statusEffect(
                new StatusEffectInstance(
                    StrangeryStatusEffects.BLEEDING,
                    4 * 20
                ),
                0.5F
            )
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
