package com.kqp.strangery.entity.mob;

import com.kqp.strangery.Strangery;
import com.kqp.strangery.entity.ai.BetterMeleeAttackGoal;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Box;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnderAgentEntity extends HostileEntity {
    private static final Item[] HELD_ITEMS = {
        Items.IRON_SWORD,
        Items.IRON_AXE
    };

    public EnderAgentEntity(EntityType type, World world) {
        super(type, world);

        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
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
        this.goalSelector.add(0, new BetterMeleeAttackGoal(this, 2.0F));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));

        this.targetSelector.add(0, new HivemindTargetGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this));
        this.targetSelector.add(5, new FollowTargetGoal(this, PlayerEntity.class, true));
    }

    @Override
    public @Nullable EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
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
    protected void initEquipment(LocalDifficulty difficulty) {
        super.initEquipment(difficulty);

        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(HELD_ITEMS[random.nextInt(HELD_ITEMS.length)]));
    }

    @Override
    public boolean hurtByWater() {
        return true;
    }

    class HivemindTargetGoal extends TrackTargetGoal {
        private EnderAgentEntity agentEntity;

        public HivemindTargetGoal(EnderAgentEntity agentEntity) {
            super(agentEntity, false, true);

            this.agentEntity = agentEntity;
        }

        @Override
        public boolean canStart() {
            List<EnderAgentEntity> fellowAgents = getNearbyAgents();

            for (EnderAgentEntity agent : fellowAgents) {
                if (agent.getAttacker() != null) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public boolean shouldContinue() {
            List<EnderAgentEntity> fellowAgents = getNearbyAgents();

            for (EnderAgentEntity otherAgent : fellowAgents) {
                if (otherAgent.getAttacker() != null && otherAgent.getLastAttackedTime() != agentEntity.getLastAttackedTime()) {
                    agentEntity.setAttacker(otherAgent.getAttacker());
                    agentEntity.lastAttackedTicks = otherAgent.getLastAttackedTime();

                    return true;
                }
            }

            return false;
        }

        private List<EnderAgentEntity> getNearbyAgents() {
            return mob.world.getEntitiesByClass(EnderAgentEntity.class, new Box(-16.0F, -16.0F, -16.0F, 16.0F, 16.0F, 16.0F), entity -> true);
        }
    }
}
