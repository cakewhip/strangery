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

    public static final SoundEvent ENDER_AGENT_AMBIENT = new SoundEvent(
        ENDER_AGENT_AMBIENT_ID
    );
    public static final SoundEvent ENDER_AGENT_DEATH = new SoundEvent(
        ENDER_AGENT_DEATH_ID
    );
    public static final SoundEvent ENDER_AGENT_HURT = new SoundEvent(
        ENDER_AGENT_HURT_ID
    );

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
    }
}
