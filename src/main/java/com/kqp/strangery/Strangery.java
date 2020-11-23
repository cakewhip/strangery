package com.kqp.strangery;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Strangery implements ModInitializer {
    public static final String MOD_ID = "strangery";

    @Override
    public void onInitialize() {
        I.init();
    }

    public static Identifier id(String name) {
        return new Identifier(MOD_ID, name);
    }

    static class I {
        public static final Item UNWIELDY_STICK = register(new Item(new Item.Settings()), "unwieldy_stick");

        public static void init() {
        }

        private static Item register(Item item, String name) {
            Registry.register(Registry.ITEM, id(name), item);

            return item;
        }
    }
}
