package com.kqp.strangery.entity.mob;

import com.kqp.strangery.init.StrangerySounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class SansEntity extends HostileEntity {

    private static int MUSIC_LENGTH = 25 * 20;

    private int musicCooldown;

    public SansEntity(EntityType type, World world) {
        super(type, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
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
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putInt("MusicCooldown", musicCooldown);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        musicCooldown = tag.getInt("MusicCooldown");
    }

    @Override
    public void playAmbientSound() {
        if (musicCooldown == 0) {
            musicCooldown = MUSIC_LENGTH;
            super.playAmbientSound();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return StrangerySounds.SANS_HURT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return StrangerySounds.SANS_HURT;
    }

    @Override
    protected float getSoundPitch() {
        return 1.0F;
    }

    @Override
    public void tickMovement() {
        if (this.isAlive()) {
            musicCooldown = Math.max(0, musicCooldown - 1);
            if (this.isAffectedByDaylight()) {
                this.setOnFireFor(8);
            }
        }

        super.tickMovement();
    }

    public static DefaultAttributeContainer.Builder createSansAttributes() {
        return HostileEntity
            .createHostileAttributes()
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.405D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0D);
    }
}
