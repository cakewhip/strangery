package com.kqp.strangery.init;

import com.kqp.strangery.entity.mob.CourierEntity;
import com.kqp.strangery.entity.mob.EnderAgentEntity;
import com.kqp.strangery.entity.mob.KyloRenEntity;
import com.kqp.strangery.entity.mob.LeeSinEntity;
import com.kqp.strangery.entity.mob.MoffGideonEntity;
import com.kqp.strangery.entity.mob.SansEntity;
import com.kqp.strangery.entity.mob.ZombieElfEntity;
import com.kqp.strangery.entity.mob.ZombieSantaEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.SpawnSettings.SpawnEntry;

public class StrangeryEntities {

    public static final Map<SpawnGroup, List<SpawnEntry>> SPAWNS = new HashMap<SpawnGroup, List<SpawnEntry>>();

    public static final EntityType<EnderAgentEntity> ENDER_AGENT = Registry.register(
        Registry.ENTITY_TYPE,
        Strangery.id("ender_agent"),
        FabricEntityTypeBuilder
            .create(SpawnGroup.MONSTER, EnderAgentEntity::new)
            .dimensions(EntityDimensions.fixed(0.75F, 1.0F))
            .trackable(72, 3)
            .build()
    );

    public static final EntityType<CourierEntity> COURIER = Registry.register(
        Registry.ENTITY_TYPE,
        Strangery.id("courier"),
        FabricEntityTypeBuilder
            .<CourierEntity>create(SpawnGroup.AMBIENT, CourierEntity::new)
            .dimensions(EntityDimensions.fixed(0.75F, 1.95F))
            .trackable(72, 3)
            .build()
    );

    public static final EntityType<LeeSinEntity> LEE_SIN = Registry.register(
        Registry.ENTITY_TYPE,
        Strangery.id("lee_sin"),
        FabricEntityTypeBuilder
            .create(SpawnGroup.MONSTER, LeeSinEntity::new)
            .dimensions(EntityDimensions.fixed(0.75F, 1.95F))
            .trackable(72, 3)
            .build()
    );

    public static final EntityType<SansEntity> SANS = Registry.register(
        Registry.ENTITY_TYPE,
        Strangery.id("sans"),
        FabricEntityTypeBuilder
            .create(SpawnGroup.MONSTER, SansEntity::new)
            .dimensions(EntityDimensions.fixed(0.75F, 1.95F))
            .trackable(72, 3)
            .build()
    );

    public static final EntityType<ZombieElfEntity> ZOMBIE_ELF = Registry.register(
        Registry.ENTITY_TYPE,
        Strangery.id("zombie_elf"),
        FabricEntityTypeBuilder
            .create(SpawnGroup.MONSTER, ZombieElfEntity::new)
            .dimensions(EntityDimensions.fixed(0.75F, 1.95F))
            .trackable(72, 3)
            .build()
    );

    public static final EntityType<ZombieSantaEntity> ZOMBIE_SANTA = Registry.register(
        Registry.ENTITY_TYPE,
        Strangery.id("zombie_santa"),
        FabricEntityTypeBuilder
            .create(SpawnGroup.MONSTER, ZombieSantaEntity::new)
            .dimensions(EntityDimensions.fixed(0.75F, 1.95F))
            .trackable(72, 3)
            .build()
    );

    public static final EntityType<MoffGideonEntity> MOFF_GIDEON = Registry.register(
        Registry.ENTITY_TYPE,
        Strangery.id("moff_gideon"),
        FabricEntityTypeBuilder
            .create(SpawnGroup.MONSTER, MoffGideonEntity::new)
            .dimensions(EntityDimensions.fixed(0.75F, 1.95F))
            .trackable(72, 3)
            .build()
    );

    public static final EntityType<KyloRenEntity> KYLO_REN = Registry.register(
        Registry.ENTITY_TYPE,
        Strangery.id("kylo_ren"),
        FabricEntityTypeBuilder
            .create(SpawnGroup.MONSTER, KyloRenEntity::new)
            .dimensions(EntityDimensions.fixed(0.75F, 1.95F))
            .trackable(72, 3)
            .build()
    );

    public static void init() {
        register(
            ENDER_AGENT,
            0x000000,
            0xCC00FA,
            EnderAgentEntity.createEnderAgentAttributes()
        );
        register(
            COURIER,
            0x3D2919,
            0x857261,
            CourierEntity.createCourierAttributes()
        );
        register(
            LEE_SIN,
            0x8F6650,
            0xC71019,
            LeeSinEntity.createLeeSinAttributes()
        );
        register(SANS, 0xCCCCCC, 0x0000CC, SansEntity.createSansAttributes());
        register(
            ZOMBIE_ELF,
            0x3BD500,
            0x42823D,
            ZombieElfEntity.createZombieElfAttributes()
        );
        register(
            ZOMBIE_SANTA,
            0xD30018,
            0x5BAF48,
            ZombieSantaEntity.createZombieSantaAttributes()
        );
        register(
            MOFF_GIDEON,
            0x1E1E1E,
            0x540009,
            MoffGideonEntity.createMoffGideonAttributes()
        );
        register(
            KYLO_REN,
            0x1E1E1E,
            0xAA0009,
            KyloRenEntity.createKyloRenAttributes()
        );
    }

    public static void initSpawns() {
        if (SPAWNS.isEmpty()) {
            addSpawn(SpawnGroup.MONSTER, spawnEntry(ENDER_AGENT, 3, 1, 1));
            addSpawn(SpawnGroup.MONSTER, spawnEntry(LEE_SIN, 1, 1, 1));
            addSpawn(SpawnGroup.MONSTER, spawnEntry(ZOMBIE_ELF, 150, 4, 4));
            addSpawn(SpawnGroup.MONSTER, spawnEntry(ZOMBIE_SANTA, 1, 1, 1));
            addSpawn(SpawnGroup.MONSTER, spawnEntry(MOFF_GIDEON, 1, 1, 1));
            addSpawn(SpawnGroup.MONSTER, spawnEntry(KYLO_REN, 1, 1, 1));
        }
    }

    private static <T extends LivingEntity> void register(
        EntityType<T> type,
        int primaryColor,
        int secondaryColor,
        DefaultAttributeContainer.Builder attributeBuilder
    ) {
        Registry.register(
            Registry.ITEM,
            new Identifier(EntityType.getId(type).toString() + "_spawn_egg"),
            new SpawnEggItem(
                type,
                primaryColor,
                secondaryColor,
                new Item.Settings().group(ItemGroup.MISC)
            )
        );

        FabricDefaultAttributeRegistry.register(type, attributeBuilder);
    }

    private static SpawnEntry spawnEntry(
        EntityType<?> type,
        int weight,
        int min,
        int max
    ) {
        return new SpawnEntry(type, weight, min, max);
    }

    private static void addSpawn(SpawnGroup spawnGroup, SpawnEntry spawnEntry) {
        SPAWNS.compute(
            spawnGroup,
            (key, val) -> {
                if (val == null) {
                    val = new ArrayList<SpawnEntry>();
                }

                val.add(spawnEntry);
                return val;
            }
        );
    }
}
