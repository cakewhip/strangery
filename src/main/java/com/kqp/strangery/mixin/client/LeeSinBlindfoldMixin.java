package com.kqp.strangery.mixin.client;

import com.kqp.strangery.init.Strangery;
import com.kqp.strangery.init.StrangeryItems;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class LeeSinBlindfoldMixin {

    private static final Identifier PUMPKIN_BLUR = Strangery.id(
        "textures/misc/lee_sin_blindfold.png"
    );

    @Shadow
    private int scaledWidth;

    @Shadow
    private int scaledHeight;

    @Inject(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerInventory;getArmorStack(I)Lnet/minecraft/item/ItemStack;"
        )
    )
    private void injectRender(
        MatrixStack matrices,
        float tickDelta,
        CallbackInfo callbackInfo
    ) {
        InGameHud igh = (InGameHud) (Object) this;
        MinecraftClient client = MinecraftClient.getInstance();

        if (
            client.options.getPerspective().isFirstPerson() &&
            TrinketsApi
                .getTrinketsInventory(client.player)
                .count(StrangeryItems.LEE_SIN_BLINDFOLD) >
            0
        ) {
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.defaultBlendFunc();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableAlphaTest();
            client.getTextureManager().bindTexture(PUMPKIN_BLUR);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
            bufferBuilder
                .vertex(0.0D, this.scaledHeight, -90.0D)
                .texture(0.0F, 1.0F)
                .next();
            bufferBuilder
                .vertex(this.scaledWidth, this.scaledHeight, -90.0D)
                .texture(1.0F, 1.0F)
                .next();
            bufferBuilder
                .vertex(this.scaledWidth, 0.0D, -90.0D)
                .texture(1.0F, 0.0F)
                .next();
            bufferBuilder.vertex(0.0D, 0.0D, -90.0D).texture(0.0F, 0.0F).next();
            tessellator.draw();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.enableAlphaTest();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
