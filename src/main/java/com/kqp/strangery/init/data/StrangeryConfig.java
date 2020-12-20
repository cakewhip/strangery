package com.kqp.strangery.init.data;

import com.kqp.strangery.init.Strangery;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;

@Config(name = Strangery.MOD_ID)
public class StrangeryConfig implements ConfigData {

    public boolean fistMiningHurts = false;

    public boolean foodOverrides = true;

    public boolean playerDebuffs = true;

    public static void init() {
        AutoConfig.register(
            StrangeryConfig.class,
            JanksonConfigSerializer::new
        );
    }

    public static StrangeryConfig get() {
        return AutoConfig.getConfigHolder(StrangeryConfig.class).getConfig();
    }
}
