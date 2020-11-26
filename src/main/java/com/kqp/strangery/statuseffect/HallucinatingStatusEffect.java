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
    private static final double AMPLIFY_CHANCE = 0.025D;

    private static final List<HallucinationSound> HALLUCINATION_SOUNDS = new ArrayList<HallucinationSound>();

    static {
        HALLUCINATION_SOUNDS.add(new HallucinationSound(SoundEvents.ENTITY_CREEPER_PRIMED, false));
        HALLUCINATION_SOUNDS.add(new HallucinationSound(SoundEvents.ENTITY_CREEPER_PRIMED, false));
        HALLUCINATION_SOUNDS.add(new HallucinationSound(SoundEvents.ENTITY_CREEPER_HURT));
        HALLUCINATION_SOUNDS.add(new HallucinationSound(SoundEvents.ENTITY_ZOMBIE_AMBIENT));
        HALLUCINATION_SOUNDS.add(new HallucinationSound(SoundEvents.ENTITY_SKELETON_AMBIENT));
        HALLUCINATION_SOUNDS.add(new HallucinationSound(SoundEvents.ENTITY_SPIDER_AMBIENT));
        HALLUCINATION_SOUNDS.add(new HallucinationSound(SoundEvents.ENTITY_ENDERMAN_AMBIENT));
        HALLUCINATION_SOUNDS.add(new HallucinationSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT));
        HALLUCINATION_SOUNDS.add(new HallucinationSound(SoundEvents.ENTITY_STRAY_AMBIENT));
    }

    public HallucinatingStatusEffect(StatusEffectType type, int color) {
        super(type, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        HallucinationSound hallucinationSound = HALLUCINATION_SOUNDS.get(RANDOM.nextInt(HALLUCINATION_SOUNDS.size()));
        entity.playSound(
            hallucinationSound.soundEvent,
            hallucinationSound.getVolume(),
            hallucinationSound.getPitch()
        );
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 20 == 0
            && RANDOM.nextDouble() < (HALLUCINATION_CHANCE + amplifier * AMPLIFY_CHANCE)
            && duration > 0;
    }

    private static class HallucinationSound {
        public final SoundEvent soundEvent;
        public final boolean mobSound;

        private HallucinationSound(SoundEvent soundEvent, boolean mobSound) {
            this.soundEvent = soundEvent;
            this.mobSound = mobSound;
        }

        private HallucinationSound(SoundEvent soundEvent) {
            this(soundEvent, true);
        }

        public float getVolume() {
            return 1.0F;
        }

        public float getPitch() {
            return mobSound ? (RANDOM.nextFloat() - RANDOM.nextFloat()) * 0.2F + 1.0F : 0.5F;
        }
    }
}
