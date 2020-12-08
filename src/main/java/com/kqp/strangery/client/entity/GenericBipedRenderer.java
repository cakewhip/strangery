package com.kqp.strangery.client.entity;

import com.kqp.strangery.client.model.GenericBipedModel;
import com.kqp.strangery.init.Strangery;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GenericBipedRenderer<T extends MobEntity, M extends GenericBipedModel<T>>
    extends BipedEntityRenderer<T, M> {

    public final Identifier texture;

    public GenericBipedRenderer(
        EntityRenderDispatcher dispatcher,
        String path
    ) {
        super(dispatcher, (M) new GenericBipedModel<T>(0F, false), 0.5F);
        this.addFeature(
                new ArmorFeatureRenderer(
                    this,
                    new GenericBipedModel<T>(0.5F, true),
                    new GenericBipedModel<T>(1F, true)
                )
            );

        this.texture = Strangery.id("textures/entity/" + path + ".png");
    }

    @Override
    public Identifier getTexture(T mobEntity) {
        return texture;
    }
}
