package com.martinsil.pvzcraft.entity.client;

import com.martinsil.pvzcraft.PvZCraft;
import com.martinsil.pvzcraft.entity.custom.PeashooterEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PeashooterModel<T extends PeashooterEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer PEASHOOTER = new EntityModelLayer(Identifier.of(PvZCraft.MOD_ID, "peashooter"), "main");

    private final ModelPart root;
    public PeashooterModel(ModelPart root) {
        this.root = root.getChild("root");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData leaves = root.addChild("leaves", ModelPartBuilder.create().uv(32, 36).cuboid(-7.0F, -2.0F, 4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 52).cuboid(-5.0F, -2.0F, 4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(14, 56).cuboid(-5.0F, -2.0F, 6.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(32, 52).cuboid(-3.0F, -2.0F, 4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(40, 0).cuboid(-3.0F, -3.0F, -4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(40, 4).cuboid(-5.0F, -3.0F, 3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(40, 40).cuboid(-3.0F, -2.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(40, 16).cuboid(-5.0F, -3.0F, 1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 40).cuboid(-5.0F, -3.0F, -3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(48, 20).cuboid(-5.0F, -3.0F, -5.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(40, 28).cuboid(-7.0F, -3.0F, 3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(40, 32).cuboid(-7.0F, -2.0F, 6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(40, 36).cuboid(-8.0F, -2.0F, 6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 44).cuboid(1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(40, 44).cuboid(3.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 44).cuboid(-3.0F, -3.0F, 2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 48).cuboid(-3.0F, -2.0F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(48, 0).cuboid(-5.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(8, 48).cuboid(-6.0F, -3.0F, 1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(48, 12).cuboid(-6.0F, -3.0F, -3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 48).cuboid(-7.0F, -3.0F, -5.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(48, 24).cuboid(-7.0F, -2.0F, -8.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 48).cuboid(-7.0F, -2.0F, -6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(48, 40).cuboid(-5.0F, -2.0F, -6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(48, 48).cuboid(-3.0F, -2.0F, -6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(48, 16).cuboid(4.0F, -3.0F, -3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(48, 28).cuboid(5.0F, -3.0F, -5.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(48, 32).cuboid(1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(40, 48).cuboid(5.0F, -2.0F, -8.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(48, 44).cuboid(5.0F, -2.0F, -6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 52).cuboid(3.0F, -2.0F, -6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(8, 52).cuboid(3.0F, -3.0F, -3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 52).cuboid(5.0F, -2.0F, 4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(40, 52).cuboid(3.0F, -2.0F, 4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 36).cuboid(1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(40, 12).cuboid(3.0F, -3.0F, 1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 40).cuboid(4.0F, -3.0F, 1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(48, 4).cuboid(6.0F, -2.0F, -8.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(48, 8).cuboid(3.0F, -3.0F, -5.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(40, 20).cuboid(5.0F, -3.0F, 3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(40, 24).cuboid(1.0F, -2.0F, 4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 36).cuboid(1.0F, -3.0F, 2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 44).cuboid(1.0F, -3.0F, -4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 40).cuboid(3.0F, -3.0F, 3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(8, 40).cuboid(5.0F, -2.0F, 6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 40).cuboid(6.0F, -2.0F, 6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(8, 56).cuboid(0.0F, -2.0F, 3.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(56, 10).cuboid(-1.0F, -2.0F, 3.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(40, 8).cuboid(-1.0F, -2.0F, 1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(8, 44).cuboid(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 44).cuboid(-1.0F, -2.0F, -5.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 56).cuboid(-8.0F, -2.0F, -8.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 4).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData stem = root.addChild("stem", ModelPartBuilder.create().uv(0, 4).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 8).cuboid(-1.0F, -5.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 8).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(8, 36).cuboid(-1.0F, -7.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(8, 0).cuboid(-1.0F, -8.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 28).cuboid(0.0F, -8.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 32).cuboid(-1.0F, -8.0F, -3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 36).cuboid(-2.0F, -8.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));

        ModelPartData head = stem.addChild("head", ModelPartBuilder.create().uv(24, 16).cuboid(1.0F, -6.0F, 1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 24).cuboid(-3.0F, -6.0F, 1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 24).cuboid(-1.0F, -6.0F, 2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 28).cuboid(-3.0F, -6.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 4).cuboid(-3.0F, -6.0F, -3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(8, 28).cuboid(-1.0F, -7.0F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 16).cuboid(-1.0F, -7.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 20).cuboid(1.0F, -6.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 32).cuboid(1.0F, -6.0F, -3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(8, 24).cuboid(-3.0F, -4.0F, -4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 8).cuboid(0.0F, -3.0F, 6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(8, 20).cuboid(-2.0F, -3.0F, 6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 12).cuboid(1.0F, -5.0F, 6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 16).cuboid(0.0F, -7.0F, 6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(56, 4).cuboid(-2.0F, -6.0F, 6.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(56, 7).cuboid(-2.0F, -7.0F, 6.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 20).cuboid(-3.0F, -5.0F, 6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(48, 36).cuboid(0.0F, -6.0F, -7.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(56, 0).cuboid(-2.0F, -6.0F, -7.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(48, 52).cuboid(-1.0F, -5.0F, -6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(8, 4).cuboid(-1.0F, -2.0F, 2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(8, 8).cuboid(1.0F, -2.0F, 1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 12).cuboid(1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(8, 12).cuboid(1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 32).cuboid(1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 16).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 12).cuboid(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 0).cuboid(-3.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 8).cuboid(-3.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 4).cuboid(-3.0F, -2.0F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(8, 16).cuboid(-3.0F, -2.0F, 1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 20).cuboid(-2.0F, -5.0F, 4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 24).cuboid(0.0F, -5.0F, 4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 20).cuboid(-1.0F, -6.0F, 4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 32).cuboid(-1.0F, -4.0F, 4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 0).cuboid(1.0F, -4.0F, 2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 8).cuboid(-3.0F, -4.0F, 2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 4).cuboid(-4.0F, -4.0F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(8, 32).cuboid(-4.0F, -4.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 12).cuboid(2.0F, -4.0F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 28).cuboid(2.0F, -4.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 28).cuboid(1.0F, -4.0F, -4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 0).cuboid(-1.0F, -3.0F, -5.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 24).cuboid(-1.0F, -6.0F, -4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -7.0F, -1.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void setAngles(PeashooterEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        this.updateAnimation(entity.idleAnimationState, PeashooterAnimations.ANIM_PEASHOOTER_IDLE, ageInTicks, 1F);

        this.updateAnimation(entity.shootAnimationState, PeashooterAnimations.ANIM_PEASHOOTER_SHOOT, ageInTicks, 1F);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        root.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return root;
    }
}
