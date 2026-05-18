package com.martinsil.pvzcraft.entity.client;

import com.martinsil.pvzcraft.PvZCraft;
import com.martinsil.pvzcraft.entity.custom.RegularZombieEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RegularZombieModel<T extends RegularZombieEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer REGULAR_ZOMBIE = new EntityModelLayer(Identifier.of(PvZCraft.MOD_ID, "regular_zombie"), "main");

    private final ModelPart root;
    public RegularZombieModel(ModelPart root) {
        this.root = root.getChild("root");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 24.0F, 5.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, 0.0F, 0.0F));

        ModelPartData cube_r1 = body.addChild("cube_r1", ModelPartBuilder.create().uv(0, 52).cuboid(-11.1F, -8.0F, -0.85F, 12.0F, 9.0F, 3.0F, new Dilation(0.1F)), ModelTransform.of(6.0F, -22.0F, -6.0F, -0.0035F, 0.1868F, -1.7078F));

        ModelPartData cube_r2 = body.addChild("cube_r2", ModelPartBuilder.create().uv(30, 52).cuboid(-1.55F, -11.9F, 0.7F, 3.0F, 12.0F, 0.0F, new Dilation(0.12F)), ModelTransform.of(1.0F, -10.0F, -6.0F, 0.1876F, 0.2148F, -0.0924F));

        ModelPartData cube_r3 = body.addChild("cube_r3", ModelPartBuilder.create().uv(30, 52).cuboid(-1.5F, -11.9F, -0.25F, 3.0F, 12.0F, 0.0F, new Dilation(0.12F)), ModelTransform.of(7.0F, -11.0F, -5.0F, 0.1772F, -0.1719F, -0.1615F));

        ModelPartData cube_r4 = body.addChild("cube_r4", ModelPartBuilder.create().uv(39, 48).cuboid(-7.5F, -12.0F, 0.1F, 1.0F, 5.0F, 0.0F, new Dilation(0.15F)), ModelTransform.of(10.0F, -9.0F, -10.0F, -0.2618F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(12, 46).cuboid(1.6F, -23.5F, -6.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.3F))
                .uv(13, 42).cuboid(1.6F, -27.0F, -6.5F, 2.0F, 2.0F, 1.0F, new Dilation(1.5F))
                .uv(2, 46).cuboid(3.2F, -27.0F, -8.3F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(4, 48).cuboid(0.3F, -26.5F, -8.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.3F))
                .uv(7, 44).cuboid(4.5F, -26.1F, -8.12F, 0.0F, 0.0F, 0.0F, new Dilation(0.2F))
                .uv(23, 46).cuboid(2.0F, -24.0F, -7.875F, 0.0F, 0.0F, 0.0F, new Dilation(0.17F))
                .uv(23, 46).cuboid(2.85F, -24.7F, -7.875F, 0.0F, 0.0F, 0.0F, new Dilation(0.17F))
                .uv(26, 46).cuboid(3.4F, -24.75F, -7.91F, 0.0F, 0.0F, 0.0F, new Dilation(0.12F))
                .uv(23, 46).cuboid(3.3F, -24.05F, -7.85F, 0.0F, 0.0F, 0.0F, new Dilation(0.2F))
                .uv(23, 46).cuboid(2.125F, -24.65F, -7.85F, 0.0F, 0.0F, 0.0F, new Dilation(0.2F))
                .uv(7, 44).cuboid(0.8F, -26.1F, -8.12F, 0.0F, 0.0F, 0.0F, new Dilation(0.2F))
                .uv(4, 40).cuboid(1.6F, -24.85F, -8.02F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData arm1 = root.addChild("arm1", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, 0.0F, 0.0F));

        ModelPartData cube_r5 = arm1.addChild("cube_r5", ModelPartBuilder.create().uv(36, 55).cuboid(-1.1F, -8.0F, -0.3F, 1.0F, 8.0F, 1.0F, new Dilation(0.475F))
                .uv(36, 55).cuboid(-12.1F, -8.0F, -0.3F, 1.0F, 8.0F, 1.0F, new Dilation(0.475F)), ModelTransform.of(9.0F, -13.0F, -8.0F, -0.2618F, 0.0F, 0.0F));

        ModelPartData cube_r6 = arm1.addChild("cube_r6", ModelPartBuilder.create().uv(43, 56).cuboid(-1.1F, -1.0F, -0.3F, 1.0F, 1.0F, 0.0F, new Dilation(0.3F)), ModelTransform.of(-2.0F, -12.0F, -8.0F, -0.2618F, 0.0F, 0.0F));

        ModelPartData cube_r7 = arm1.addChild("cube_r7", ModelPartBuilder.create().uv(43, 56).cuboid(-1.1F, -1.4F, 0.35F, 1.0F, 1.0F, 0.0F, new Dilation(0.5F)), ModelTransform.of(-2.0F, -10.0F, -9.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData cube_r8 = arm1.addChild("cube_r8", ModelPartBuilder.create().uv(47, 54).cuboid(0.15F, -1.0F, 0.9F, 0.0F, 1.0F, 0.0F, new Dilation(0.245F)), ModelTransform.of(-3.0F, -9.0F, -10.0F, -0.1744F, 0.0076F, 0.043F));

        ModelPartData cube_r9 = arm1.addChild("cube_r9", ModelPartBuilder.create().uv(47, 54).cuboid(0.6F, -1.0F, 0.9F, 0.0F, 1.0F, 0.0F, new Dilation(0.245F)), ModelTransform.of(-4.0F, -9.0F, -10.0F, -0.1744F, 0.0076F, 0.043F));

        ModelPartData cube_r10 = arm1.addChild("cube_r10", ModelPartBuilder.create().uv(47, 54).cuboid(1.0F, -1.0F, 1.3F, 0.0F, 1.0F, 0.0F, new Dilation(0.245F)), ModelTransform.of(-2.0F, -10.0F, -10.0F, -0.1434F, -0.0998F, -0.6037F));

        ModelPartData cube_r11 = arm1.addChild("cube_r11", ModelPartBuilder.create().uv(47, 54).cuboid(-0.3F, -1.0F, 0.9F, 0.0F, 1.0F, 0.0F, new Dilation(0.245F)), ModelTransform.of(-2.0F, -9.0F, -10.0F, -0.1744F, 0.0076F, 0.043F));

        ModelPartData cube_r12 = arm1.addChild("cube_r12", ModelPartBuilder.create().uv(47, 54).cuboid(-0.65F, -1.0F, 0.9F, 0.0F, 1.0F, 0.0F, new Dilation(0.245F)), ModelTransform.of(-1.0F, -9.0F, -10.0F, -0.1731F, -0.0227F, -0.1289F));

        ModelPartData arm2 = root.addChild("arm2", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, 1.0F, 0.0F));

        ModelPartData cube_r13 = arm2.addChild("cube_r13", ModelPartBuilder.create().uv(47, 54).cuboid(-0.65F, -1.0F, 0.9F, 0.0F, 1.0F, 0.0F, new Dilation(0.245F)), ModelTransform.of(10.0F, -10.0F, -10.0F, -0.1731F, -0.0227F, -0.1289F));

        ModelPartData cube_r14 = arm2.addChild("cube_r14", ModelPartBuilder.create().uv(47, 54).cuboid(-0.2F, -1.0F, 0.9F, 0.0F, 1.0F, 0.0F, new Dilation(0.245F)), ModelTransform.of(9.0F, -10.0F, -10.0F, -0.1731F, -0.0227F, -0.1289F));

        ModelPartData cube_r15 = arm2.addChild("cube_r15", ModelPartBuilder.create().uv(47, 54).cuboid(0.9F, -1.5F, 1.3F, 0.0F, 1.0F, 0.0F, new Dilation(0.245F)), ModelTransform.of(6.0F, -11.0F, -10.0F, -0.1615F, 0.0665F, 0.3873F));

        ModelPartData cube_r16 = arm2.addChild("cube_r16", ModelPartBuilder.create().uv(47, 54).cuboid(0.6F, -1.0F, 0.9F, 0.0F, 1.0F, 0.0F, new Dilation(0.245F)), ModelTransform.of(7.0F, -10.0F, -10.0F, -0.1744F, 0.0076F, 0.043F));

        ModelPartData cube_r17 = arm2.addChild("cube_r17", ModelPartBuilder.create().uv(47, 54).cuboid(0.25F, -1.0F, 0.9F, 0.0F, 1.0F, 0.0F, new Dilation(0.245F)), ModelTransform.of(8.0F, -10.0F, -10.0F, -0.1731F, -0.0227F, -0.1289F));

        ModelPartData cube_r18 = arm2.addChild("cube_r18", ModelPartBuilder.create().uv(43, 56).cuboid(-1.1F, -1.4F, 0.35F, 1.0F, 1.0F, 0.0F, new Dilation(0.5F)), ModelTransform.of(9.0F, -11.0F, -9.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData cube_r19 = arm2.addChild("cube_r19", ModelPartBuilder.create().uv(43, 56).cuboid(-1.1F, -1.0F, -0.3F, 1.0F, 1.0F, 0.0F, new Dilation(0.3F)), ModelTransform.of(9.0F, -13.0F, -8.0F, -0.2618F, 0.0F, 0.0F));

        ModelPartData leg1 = root.addChild("leg1", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, 0.0F, 0.0F));

        ModelPartData cube_r20 = leg1.addChild("cube_r20", ModelPartBuilder.create().uv(52, 54).cuboid(-0.95F, -7.0F, -1.25F, 2.0F, 7.0F, 2.0F, new Dilation(0.3F)), ModelTransform.of(1.0F, -4.0F, -3.0F, 0.0F, -0.1745F, 0.0F));

        ModelPartData cube_r21 = leg1.addChild("cube_r21", ModelPartBuilder.create().uv(52, 47).cuboid(-0.95F, -4.0F, -1.15F, 2.0F, 4.0F, 2.0F, new Dilation(0.3F)), ModelTransform.of(0.0F, -1.0F, -3.0F, -0.1122F, -0.4677F, 0.2449F));

        ModelPartData cube_r22 = leg1.addChild("cube_r22", ModelPartBuilder.create().uv(53, 26).cuboid(-0.9F, -1.0F, -1.55F, 2.0F, 1.0F, 1.0F, new Dilation(0.15F)), ModelTransform.of(-1.0F, 0.0F, -2.0F, 0.0F, -0.7418F, 0.0F));

        ModelPartData cube_r23 = leg1.addChild("cube_r23", ModelPartBuilder.create().uv(50, 28).cuboid(-1.0F, -1.0F, -2.75F, 2.0F, 1.0F, 4.0F, new Dilation(0.2F)), ModelTransform.of(1.0F, 0.0F, -4.0F, 0.0F, -0.7418F, 0.0F));

        ModelPartData leg2 = root.addChild("leg2", ModelPartBuilder.create().uv(46, 57).cuboid(6.0F, -8.0F, -4.4F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 0.0F, 0.0F));

        ModelPartData cube_r24 = leg2.addChild("cube_r24", ModelPartBuilder.create().uv(52, 35).cuboid(-0.9F, -4.0F, -0.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.3F)), ModelTransform.of(7.0F, -7.0F, -4.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData cube_r25 = leg2.addChild("cube_r25", ModelPartBuilder.create().uv(52, 42).cuboid(-1.5F, -2.0F, -1.09F, 2.0F, 2.0F, 2.0F, new Dilation(0.3F)), ModelTransform.of(7.0F, -1.0F, -4.0F, 0.0F, 0.4363F, 0.0F));

        ModelPartData cube_r26 = leg2.addChild("cube_r26", ModelPartBuilder.create().uv(53, 26).cuboid(-1.1F, -1.0F, -0.975F, 2.0F, 1.0F, 1.0F, new Dilation(0.15F)), ModelTransform.of(7.0F, 0.0F, -3.0F, 0.0F, 0.4363F, 0.0F));

        ModelPartData cube_r27 = leg2.addChild("cube_r27", ModelPartBuilder.create().uv(50, 28).cuboid(-1.0F, -1.0F, -2.75F, 2.0F, 1.0F, 4.0F, new Dilation(0.2F)), ModelTransform.of(6.0F, 0.0F, -5.0F, 0.0F, 0.4363F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        root.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return null;
    }
}
