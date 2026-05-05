package com.martinsil.pvzcraft.entity.client;

import com.martinsil.pvzcraft.PvZCraft;
import com.martinsil.pvzcraft.entity.custom.PeashooterEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PeashooterRenderer extends MobEntityRenderer<PeashooterEntity, PeashooterModel<PeashooterEntity>> {

    public PeashooterRenderer(EntityRendererFactory.Context context) {
        super(context, new PeashooterModel<>(context.getPart(PeashooterModel.PEASHOOTER)), 0.75f);
    }

    @Override
    public Identifier getTexture(PeashooterEntity entity) {
        return Identifier.of(PvZCraft.MOD_ID, "textures/entity/peashooter/peashooter.png");
    }

    @Override
    public void render(PeashooterEntity livingEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.scale(1f, 1f, 1f);

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
