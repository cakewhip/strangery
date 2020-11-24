package com.kqp.strangery;

import com.kqp.strangery.statuseffect.CustomStatusEffect;
import com.kqp.strangery.statuseffect.HealthStatusEffect;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Strangery implements ModInitializer {
    public static final String MOD_ID = "strangery";

    @Override
    public void onInitialize() {
        I.init();
        SE.init();
    }

    public static Identifier id(String name) {
        return new Identifier(MOD_ID, name);
    }

    public static class I {
        public static final Item UNWIELDY_STICK =
            register(new Item(new Item.Settings()), "unwieldy_stick");

        public static void init() {
        }

        private static Item register(Item item, String name) {
            Registry.register(Registry.ITEM, id(name), item);

            return item;
        }
    }

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

        public static void init() {
        }

        private static StatusEffect register(StatusEffect statusEffect, String name) {
            Registry.register(Registry.STATUS_EFFECT, id(name), statusEffect);

            return statusEffect;
        }
    }
}
