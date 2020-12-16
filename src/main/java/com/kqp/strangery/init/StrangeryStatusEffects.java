package com.kqp.strangery.init;

import com.kqp.strangery.statuseffect.CustomStatusEffect;
import com.kqp.strangery.statuseffect.HallucinatingStatusEffect;
import com.kqp.strangery.statuseffect.HealthStatusEffect;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.registry.Registry;

public class StrangeryStatusEffects {

    public static final StatusEffect DEAF = register(
        new CustomStatusEffect(StatusEffectType.HARMFUL, 0xFFFFFF),
        "deaf"
    );
    public static final StatusEffect BLEEDING = register(
        new HealthStatusEffect(
            StatusEffectType.HARMFUL,
            0xFFFFFF,
            DamageSource.GENERIC,
            2,
            2,
            15
        ),
        "bleeding"
    );
    public static final StatusEffect FROSTBITE = register(
        new HealthStatusEffect(
            StatusEffectType.HARMFUL,
            0xFFFFFF,
            DamageSource.GENERIC,
            2,
            2,
            25
        ),
        "frostbite"
    );
    public static final StatusEffect CONFUSION = register(
        new CustomStatusEffect(StatusEffectType.HARMFUL, 0xFFFFFF),
        "confusion"
    );
    public static final StatusEffect HALLUCINATING = register(
        new HallucinatingStatusEffect(StatusEffectType.HARMFUL, 0xFFFFFF),
        "hallucinating"
    );

    public static void init() {}

    private static StatusEffect register(
        StatusEffect statusEffect,
        String name
    ) {
        Registry.register(
            Registry.STATUS_EFFECT,
            Strangery.id(name),
            statusEffect
        );

        return statusEffect;
    }
}
