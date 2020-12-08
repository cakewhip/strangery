package com.kqp.strangery.init;

import com.kqp.strangery.enchantment.AirLoadingEnchantment;
import com.kqp.strangery.enchantment.SlingEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

public class StrangeryEnchantments {

    public static Enchantment SLING = Registry.register(
        Registry.ENCHANTMENT,
        Strangery.id("sling"),
        new SlingEnchantment()
    );

    public static Enchantment AIR_LOADING = Registry.register(
        Registry.ENCHANTMENT,
        Strangery.id("air_loading"),
        new AirLoadingEnchantment()
    );

    public static void init() {}
}
