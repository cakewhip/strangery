package com.kqp.strangery.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StrangerySounds {

    public static final Identifier ENDER_AGENT_AMBIENT_ID = Strangery.id(
        "entity.ender_agent.ambient"
    );
    public static final Identifier ENDER_AGENT_DEATH_ID = Strangery.id(
        "entity.ender_agent.death"
    );
    public static final Identifier ENDER_AGENT_HURT_ID = Strangery.id(
        "entity.ender_agent.hurt"
    );
    public static final Identifier SANS_MUSIC_ID = Strangery.id(
        "entity.sans.music"
    );
    public static final Identifier SANS_HURT_ID = Strangery.id(
        "entity.sans.hurt"
    );
    public static final Identifier PRESENT_UNWRAP_ID = Strangery.id(
        "item.present.unwrap"
    );
    public static final Identifier ZOMBIE_SANTA_AMBIENT_ID = Strangery.id(
        "entity.zombie_santa.ambient"
    );
    public static final Identifier MOFF_GIDEON_AMBIENT_ID = Strangery.id(
        "entity.moff_gideon.ambient"
    );

    public static final SoundEvent ENDER_AGENT_AMBIENT = new SoundEvent(
        ENDER_AGENT_AMBIENT_ID
    );
    public static final SoundEvent ENDER_AGENT_DEATH = new SoundEvent(
        ENDER_AGENT_DEATH_ID
    );
    public static final SoundEvent ENDER_AGENT_HURT = new SoundEvent(
        ENDER_AGENT_HURT_ID
    );
    public static final SoundEvent SANS_MUSIC = new SoundEvent(SANS_MUSIC_ID);
    public static final SoundEvent SANS_HURT = new SoundEvent(SANS_HURT_ID);
    public static final SoundEvent ZOMBIE_SANTA_AMBIENT = new SoundEvent(ZOMBIE_SANTA_AMBIENT_ID);
    public static final SoundEvent PRESENT_UNWRAP = new SoundEvent(PRESENT_UNWRAP_ID);
    public static final SoundEvent MOFF_GIDEON_AMBIENT = new SoundEvent(MOFF_GIDEON_AMBIENT_ID);

    public static void init() {
        Registry.register(
            Registry.SOUND_EVENT,
            ENDER_AGENT_AMBIENT_ID,
            ENDER_AGENT_AMBIENT
        );
        Registry.register(
            Registry.SOUND_EVENT,
            ENDER_AGENT_DEATH_ID,
            ENDER_AGENT_DEATH
        );
        Registry.register(
            Registry.SOUND_EVENT,
            ENDER_AGENT_HURT_ID,
            ENDER_AGENT_HURT
        );
        Registry.register(Registry.SOUND_EVENT, SANS_MUSIC_ID, SANS_MUSIC);
        Registry.register(Registry.SOUND_EVENT, SANS_HURT_ID, SANS_HURT);
    }
}
