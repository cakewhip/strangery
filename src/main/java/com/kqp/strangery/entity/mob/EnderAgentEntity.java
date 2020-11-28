package com.kqp.strangery.entity.mob;

import com.kqp.strangery.Strangery;
import com.kqp.strangery.entity.ai.MoveToTargetGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EnderAgentEntity extends HostileEntity {
    private static final int PLAYER_FLEE_DISTANCE = 16;
    private static final int KIDNAP_DISTANCE = 32;
    private static final int KIDNAP_PLAYER_FLEE_DISTANCE = 48;

    private static final TargetPredicate ANIMAL_TARGET_PREDICATE = new TargetPredicate()
        .setPredicate((animal) -> !(animal.getVehicle() instanceof EnderAgentEntity));

    public EnderAgentEntity(EntityType type, World world) {
        super(type, world);

        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.stepHeight = 1.6F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return Strangery.SND.ENDER_AGENT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Strangery.SND.ENDER_AGENT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Strangery.SND.ENDER_AGENT_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.8F;
    }

    @Override
    protected float getSoundPitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.3F;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new FleeEntityGoal<PlayerEntity>(
            this,
            PlayerEntity.class,
            PLAYER_FLEE_DISTANCE,
            1.15D,
            1.15D
        ));
        this.goalSelector.add(1, new MoveToTargetGoal(
            this,
            1.15D,
            KIDNAP_DISTANCE
        ));
        this.goalSelector.add(2, new FleeEntityGoal<PlayerEntity>(
            this,
            PlayerEntity.class,
            // Ender agent kills kidnapped animal once at this range, so must flee beyond
            KIDNAP_PLAYER_FLEE_DISTANCE + 16,
            1D,
            1D
        ));
        this.goalSelector.add(3, new LookAtEntityGoal(this, AnimalEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
    }

    @Override
    public void tickMovement() {
        // TODO maybe move this to a target goal class
        if (!world.isClient && age % 10 == 0) {
            if (!this.hasPassengers()) {
                LivingEntity target = this.getTarget();

                if (target != null && this.distanceTo(target) < 1.25D) {
                    target.startRiding(this, true);
                    this.setTarget(null);
                } else if (target == null || this.distanceTo(target) > KIDNAP_DISTANCE) {
                    this.setTarget(this.world.getClosestEntityIncludingUngeneratedChunks(
                        AnimalEntity.class,
                        ANIMAL_TARGET_PREDICATE,
                        this,
                        this.getX(),
                        this.getEyeY(),
                        this.getZ(),
                        this.getBoundingBox().expand(48D)
                    ));
                }
            }
        }

        super.tickMovement();
    }

    @Override
    public void baseTick() {
        super.baseTick();

        if (this.hasPassengers()) {
            PlayerEntity player = world.getClosestPlayer(
                this.getX(),
                this.getY(),
                this.getZ(),
                KIDNAP_PLAYER_FLEE_DISTANCE,
                true
            );

            if (player == null) {
                for (Entity passenger : this.getPassengerList()) {
                    if (passenger instanceof LivingEntity) {
                        passenger.damage(
                            DamageSource.MAGIC,
                            Float.MAX_VALUE
                        );
                    }
                }
            }
        }
    }

    @Override
    public @Nullable EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
                                           SpawnReason spawnReason, @Nullable EntityData entityData,
                                           @Nullable CompoundTag entityTag) {
        this.initEquipment(difficulty);

        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    public static DefaultAttributeContainer.Builder createEnderAgentAttributes() {
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.365D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.5D)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D);
    }

    @Override
    public boolean hurtByWater() {
        return true;
    }

    @Override
    public double getMountedHeightOffset() {
        return 0.8D;
    }
}
