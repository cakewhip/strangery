package com.kqp.strangery.init.client;

import com.kqp.strangery.client.entity.EnderAgentRenderer;
import com.kqp.strangery.client.entity.GenericBipedRenderer;
import com.kqp.strangery.init.StrangeryBlocks;
import com.kqp.strangery.init.StrangeryEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class StrangeryClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(
            StrangeryBlocks.XMAS_LIGHTS,
            RenderLayer.getCutout()
        );

        // BlockRenderLayerMap.INSTANCE.putBlock(B.STAR, RenderLayer.getCutout());

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
