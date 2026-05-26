package com.martinsil.pvzcraft.entity.client;

import com.martinsil.pvzcraft.PvZCraft;
import com.martinsil.pvzcraft.entity.custom.RegularZombieEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class RegularZombieRenderer extends MobEntityRenderer<RegularZombieEntity, RegularZombieModel<RegularZombieEntity>> {

    public RegularZombieRenderer(EntityRendererFactory.Context context) {
        super(context, new RegularZombieModel<>(context.getPart(RegularZombieModel.REGULAR_ZOMBIE)), 0.5f);
    }

    @Override
    public Identifier getTexture(RegularZombieEntity entity) {
        return Identifier.of(PvZCraft.MOD_ID, "textures/entity/regular_zombie/regular_zombie.png");
    }

    @Override
    public void render(RegularZombieEntity livingEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.scale(1f, 1f, 1f);

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
