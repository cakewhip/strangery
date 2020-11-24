package com.kqp.strangery.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HallucinatingStatusEffect extends CustomStatusEffect {
    private static final Random RANDOM = new Random();

    private static final double HALLUCINATION_CHANCE = 0.05D;
    private static final double AMPLIFY_CHANCE = 0.05D;

    private static final List<SoundEvent> SOUND_IDS = new ArrayList<SoundEvent>();

    static {
        SOUND_IDS.add(SoundEvents.ENTITY_CREEPER_PRIMED);
        SOUND_IDS.add(SoundEvents.ENTITY_CREEPER_PRIMED);
        SOUND_IDS.add(SoundEvents.ENTITY_CREEPER_HURT);
        SOUND_IDS.add(SoundEvents.ENTITY_ZOMBIE_AMBIENT);
        SOUND_IDS.add(SoundEvents.ENTITY_SKELETON_AMBIENT);
        SOUND_IDS.add(SoundEvents.ENTITY_SPIDER_AMBIENT);
        SOUND_IDS.add(SoundEvents.ENTITY_ENDERMAN_AMBIENT);
        SOUND_IDS.add(SoundEvents.ENTITY_ENDERMAN_TELEPORT);
        SOUND_IDS.add(SoundEvents.ENTITY_STRAY_AMBIENT);
    }

    public HallucinatingStatusEffect(StatusEffectType type, int color) {
        super(type, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.playSound(
            SOUND_IDS.get(RANDOM.nextInt(SOUND_IDS.size())),
            1.0F,
            0.5F
        );
        System.out.println(amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 20 == 0
            && RANDOM.nextDouble() < (HALLUCINATION_CHANCE + amplifier * AMPLIFY_CHANCE)
            && duration > 0;
    }
}
