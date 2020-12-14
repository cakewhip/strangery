package com.kqp.strangery.init;

import com.kqp.strangery.init.data.FoodDataOverrides;
import com.kqp.strangery.init.data.StrangeryConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Strangery implements ModInitializer {

    public static final String MOD_ID = "strangery";

    @Override
    public void onInitialize() {
        StrangeryConfig.init();

        StrangeryEnchantments.init();
        StrangerySounds.init();

        StrangeryBlocks.init();
        StrangeryItems.init();

        StrangeryEntities.init();
        StrangeryStatusEffects.init();

        StrangeryWorldFeatures.init();

        FoodDataOverrides.init();
    }

    public static Identifier id(String name) {
        return new Identifier(MOD_ID, name);
    }
}
