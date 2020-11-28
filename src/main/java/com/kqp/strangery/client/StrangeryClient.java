package com.kqp.strangery.client;

import com.kqp.strangery.Strangery;
import com.kqp.strangery.client.entity.EnderAgentRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class StrangeryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(
            Strangery.E.ENDER_AGENT,
            (dispatcher, context) -> new EnderAgentRenderer(dispatcher)
        );
    }
}
