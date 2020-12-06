package com.kqp.strangery.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectType;

public class HealthStatusEffect extends CustomStatusEffect {

  private final DamageSource damageSource;
  private final int damageAmount;
  private final int amplifyAmount;
  private final int tick;

  public HealthStatusEffect(
    StatusEffectType type,
    int color,
    DamageSource damageSource,
    int damageAmount,
    int amplifyAmount,
    int tick
  ) {
    super(type, color);
    this.damageSource = damageSource;
    this.amplifyAmount = amplifyAmount;
    this.damageAmount = damageAmount;
    this.tick = tick;
  }

  public HealthStatusEffect(
    StatusEffectType type,
    int color,
    int healAmount,
    int amplifyAmount,
    int tick
  ) {
    this(type, color, null, -healAmount, amplifyAmount, tick);
  }

  @Override
  public void applyUpdateEffect(LivingEntity entity, int amplifier) {
    if (damageAmount > 0) {
      entity.damage(damageSource, damageAmount + amplifier * amplifyAmount);
    } else {
      entity.heal(-damageAmount + amplifier * amplifyAmount);
    }
  }

  @Override
  public boolean canApplyUpdateEffect(int duration, int amplifier) {
    return duration % tick == 0 && duration > 0;
  }
}
