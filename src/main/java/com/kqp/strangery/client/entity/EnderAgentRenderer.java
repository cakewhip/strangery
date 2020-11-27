package com.kqp.strangery.client.entity;

import com.kqp.strangery.Strangery;
import com.kqp.strangery.client.model.EnderAgentModel;
import com.kqp.strangery.mixin.client.accessor.HeldItemFeatureRendererAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class EnderAgentRenderer<T extends MobEntity, M extends BipedEntityModel<T>>
    extends BipedEntityRenderer<T, M> {
    private static final Identifier TEXTURE = Strangery.id("textures/entity/ender_agent.png");

    public EnderAgentRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, (M) new EnderAgentModel(), 0.5F);

        for (int i = 0; i < features.size(); i++) {
            if (features.get(i) instanceof HeldItemFeatureRenderer) {
                features.remove(i);
            }
        }

        addFeature(new CustomHeldItemFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(T mobEntity) {
        return TEXTURE;
    }

    class CustomHeldItemFeatureRenderer<T extends LivingEntity, M extends EntityModel<T> & ModelWithArms>
        extends HeldItemFeatureRenderer<T, M> {
        public CustomHeldItemFeatureRenderer(FeatureRendererContext featureRendererContext) {
            super(featureRendererContext);
        }

        @Override
        public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider,
                           int i, T livingEntity, float f, float g, float h, float j, float k,
                           float l) {
            boolean bl = livingEntity.getMainArm() == Arm.RIGHT;
            ItemStack itemStack =
                bl ? livingEntity.getOffHandStack() : livingEntity.getMainHandStack();
            ItemStack itemStack2 =
                bl ? livingEntity.getMainHandStack() : livingEntity.getOffHandStack();
            if (!itemStack.isEmpty() || !itemStack2.isEmpty()) {
                matrixStack.push();

                matrixStack.translate(0.0D, -0.25D, 0.0D);

                ((HeldItemFeatureRendererAccessor) this).callRenderItem(livingEntity, itemStack2,
                    ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, Arm.RIGHT, matrixStack,
                    vertexConsumerProvider, i);
                ((HeldItemFeatureRendererAccessor) this).callRenderItem(livingEntity, itemStack,
                    ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND, Arm.LEFT, matrixStack,
                    vertexConsumerProvider, i);
                matrixStack.pop();
            }
        }
    }
}
