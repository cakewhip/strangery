package com.kqp.strangery.entity.mob;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;

public class ZombieElfEntity extends ZombieEntity {

    public ZombieElfEntity(EntityType type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createZombieZombieAttributes() {
        return HostileEntity
            .createHostileAttributes()
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23000000417232513D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D)
            .add(EntityAttributes.GENERIC_ARMOR, 2.0D)
            .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }
}
