package com.martinsil.pvzcraft.entity.client;

import com.martinsil.pvzcraft.PvZCraft;
import com.martinsil.pvzcraft.entity.custom.LawnmowerEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LawnmowerModel<T extends LawnmowerEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer LAWNMOWER = new EntityModelLayer(Identifier.of(PvZCraft.MOD_ID, "lawnmower"), "main");

    private final ModelPart bb_main;
    public LawnmowerModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 28).cuboid(-3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 28).cuboid(2.0F, -2.0F, -5.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 28).cuboid(-3.0F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 28).cuboid(2.0F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(9, 23).cuboid(-2.0F, -2.2F, -5.5F, 4.0F, 2.0F, 7.0F, new Dilation(0.0F))
                .uv(0, 22).cuboid(-3.6F, -2.0F, -5.0F, 2.0F, 2.0F, 2.0F, new Dilation(-0.55F))
                .uv(0, 22).cuboid(-3.6F, -2.0F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(-0.55F))
                .uv(0, 22).cuboid(1.6F, -2.0F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(-0.55F))
                .uv(0, 22).cuboid(1.6F, -2.0F, -5.0F, 2.0F, 2.0F, 2.0F, new Dilation(-0.55F))
                .uv(0, 17).cuboid(-3.0F, -1.5F, -2.5F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 17).cuboid(1.0F, -1.5F, -2.5F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(10, 16).cuboid(-1.0F, -2.85F, -3.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(20, 16).cuboid(-1.5F, -3.75F, -3.5F, 3.0F, 1.0F, 3.0F, new Dilation(-0.1F))
                .uv(24, 11).cuboid(-1.75F, -3.25F, -2.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(22, 11).cuboid(-1.0F, -4.4F, -2.75F, 2.0F, 1.0F, 2.0F, new Dilation(-0.25F))
                .uv(25, 30).cuboid(-1.0F, -4.15F, -2.75F, 2.0F, 0.0F, 2.0F, new Dilation(-0.15F))
                .uv(24, 9).cuboid(-2.25F, -2.5F, -1.25F, 2.0F, 0.0F, 0.0F, new Dilation(0.2F))
                .uv(13, 11).cuboid(-2.37F, -2.5F, -1.25F, 0.0F, 0.0F, 0.0F, new Dilation(0.1F))
                .uv(13, 11).cuboid(-3.0F, -1.0F, -4.0F, 0.0F, 0.0F, 0.0F, new Dilation(0.1F))
                .uv(13, 11).cuboid(-3.0F, -1.0F, 1.0F, 0.0F, 0.0F, 0.0F, new Dilation(0.1F))
                .uv(13, 11).cuboid(3.0F, -1.0F, 1.0F, 0.0F, 0.0F, 0.0F, new Dilation(0.1F))
                .uv(13, 11).cuboid(3.0F, -1.0F, -4.0F, 0.0F, 0.0F, 0.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData cube_r1 = bb_main.addChild("cube_r1", ModelPartBuilder.create().uv(1, 6).cuboid(-1.56F, -4.9F, -0.33F, 1.0F, 4.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(3.0F, -8.0F, 5.0F, 0.0F, -0.6109F, -1.5708F));

        ModelPartData cube_r2 = bb_main.addChild("cube_r2", ModelPartBuilder.create().uv(1, 6).cuboid(-1.75F, -4.8F, -0.4F, 1.0F, 4.0F, 1.0F, new Dilation(-0.2F)), ModelTransform.of(3.0F, -5.0F, 3.0F, 0.0F, -0.6109F, -1.5708F));

        ModelPartData cube_r3 = bb_main.addChild("cube_r3", ModelPartBuilder.create().uv(1, 6).cuboid(-0.8F, -6.65F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(-0.1F))
                .uv(1, 6).cuboid(-4.2F, -6.65F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(2.0F, -2.0F, 1.0F, -0.6109F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        bb_main.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return null;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
