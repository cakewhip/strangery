package com.kqp.strangery.init.client;

import com.kqp.strangery.client.entity.EnderAgentRenderer;
import com.kqp.strangery.client.entity.GenericBipedRenderer;
import com.kqp.strangery.init.StrangeryEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class StrangeryClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(
            StrangeryEntities.ENDER_AGENT,
            (dispatcher, context) -> new EnderAgentRenderer(dispatcher)
        );

        EntityRendererRegistry.INSTANCE.register(
            StrangeryEntities.COURIER,
            (dispatcher, context) ->
                new GenericBipedRenderer(dispatcher, "courier")
        );

        EntityRendererRegistry.INSTANCE.register(
            StrangeryEntities.LEE_SIN,
            (dispatcher, context) ->
                new GenericBipedRenderer(dispatcher, "lee_sin")
        );
    }
}
