package com.kqp.strangery.client;

import com.kqp.strangery.Strangery;
import com.kqp.strangery.Strangery.B;
import com.kqp.strangery.client.entity.EnderAgentRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class StrangeryClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(
            B.XMAS_LIGHTS,
            RenderLayer.getCutout()
        );

        EntityRendererRegistry.INSTANCE.register(
            Strangery.E.ENDER_AGENT,
            (dispatcher, context) -> new EnderAgentRenderer(dispatcher)
        );
    }
}
