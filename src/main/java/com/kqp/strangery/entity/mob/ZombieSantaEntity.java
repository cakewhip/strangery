package com.kqp.strangery.entity.mob;

import com.kqp.strangery.init.StrangerySounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class ZombieSantaEntity extends ZombieEntity {

    public ZombieSantaEntity(EntityType type, World world) {
        super(type, world);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return StrangerySounds.ZOMBIE_SANTA_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return StrangerySounds.ZOMBIE_SANTA_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return StrangerySounds.ZOMBIE_SANTA_AMBIENT;
    }

    public static DefaultAttributeContainer.Builder createZombieSantaAttributes() {
        return HostileEntity
            .createHostileAttributes()
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3000000417232513D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0D)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 80.0D)
            .add(EntityAttributes.GENERIC_ARMOR, 20.0D)
            .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }
}
