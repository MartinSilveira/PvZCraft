package com.martinsil.pvzcraft.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;

public class SunRenderer extends ItemEntityRenderer {

    public SunRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(ItemEntity itemEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();

        // Make the sun bigger than it's model
        matrixStack.scale(2.0F, 2.0F, 2.0F);

        super.render(itemEntity, f, g, matrixStack, vertexConsumerProvider, i);

        matrixStack.pop();
    }
}
