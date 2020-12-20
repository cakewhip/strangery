package com.kqp.strangery.entity.mob;

import com.kqp.strangery.init.StrangeryItems;
import com.kqp.strangery.init.StrangerySounds;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MoffGideonEntity extends HostileEntity {

    public MoffGideonEntity(EntityType type, World world) {
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

    public void tickMovement() {
        if (this.isAlive()) {
            if (this.isAffectedByDaylight()) {
                this.setOnFireFor(8);
            }
        }

        super.tickMovement();
    }

    @Override
    @Nullable
    public EntityData initialize(
        ServerWorldAccess world,
        LocalDifficulty difficulty,
        SpawnReason spawnReason,
        @Nullable EntityData entityData,
        @Nullable CompoundTag entityTag
    ) {
        this.initEquipment(difficulty);

        return super.initialize(
            world,
            difficulty,
            spawnReason,
            entityData,
            entityTag
        );
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return StrangerySounds.MOFF_GIDEON_AMBIENT;
    }

    @Override
    protected void initEquipment(LocalDifficulty difficulty) {
        super.initEquipment(difficulty);

        this.equipStack(
                EquipmentSlot.MAINHAND,
                new ItemStack(StrangeryItems.DARK_SABER)
            );
    }

    @Override
    protected void dropEquipment(
        DamageSource source,
        int lootingMultiplier,
        boolean allowDrops
    ) {}

    public static DefaultAttributeContainer.Builder createMoffGideonAttributes() {
        return HostileEntity
            .createHostileAttributes()
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.29D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0D)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0D)
            .add(EntityAttributes.GENERIC_ARMOR, 20.0D);
    }
}
