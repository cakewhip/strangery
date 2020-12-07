package com.kqp.strangery.entity.mob;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class LeeSinEntity extends HostileEntity {

    public LeeSinEntity(EntityType type, World world) {
        super(type, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new MeleeAttackGoal(this, 1.0F, true));
        this.goalSelector.add(
                1,
                new LookAtEntityGoal(this, PlayerEntity.class, 8.0F)
            );
        this.goalSelector.add(2, new LookAroundGoal(this));

        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.goalSelector.add(0, new WanderAroundFarGoal(this, 1.0D));

        this.targetSelector.add(
                0,
                new FollowTargetGoal(this, PlayerEntity.class, true)
            );
        this.targetSelector.add(
                1,
                new FollowTargetGoal(this, VillagerEntity.class, false)
            );
        this.targetSelector.add(
                1,
                new FollowTargetGoal(this, MerchantEntity.class, false)
            );
        this.targetSelector.add(
                2,
                new FollowTargetGoal(this, IronGolemEntity.class, true)
            );
    }

    @Override
    public boolean tryAttack(Entity target) {
        float attackDamage = (float) this.getAttributeValue(
                EntityAttributes.GENERIC_ATTACK_DAMAGE
            );
        if (target instanceof LivingEntity) {
            attackDamage +=
                EnchantmentHelper.getAttackDamage(
                    this.getMainHandStack(),
                    ((LivingEntity) target).getGroup()
                );
        }

        boolean damaged = target.damage(DamageSource.mob(this), attackDamage);
        if (damaged) {
            target.setVelocity(
                target.getVelocity().x,
                target.getVelocity().y + 1.5D,
                target.getVelocity().z
            );

            this.setVelocity(this.getVelocity().multiply(0.6D, 1.0D, 0.6D));

            this.dealDamage(this, target);
            this.onAttacking(target);
        }

        return damaged;
    }

    public static DefaultAttributeContainer.Builder createLeeSinAttributes() {
        return HostileEntity
            .createHostileAttributes()
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.355D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0D);
    }
}
