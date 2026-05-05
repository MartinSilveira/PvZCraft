package com.martinsil.pvzcraft.entity.client;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class PeashooterAnimations {
    public static final Animation ANIM_PEASHOOTER_IDLE = Animation.Builder.create(2.5F).looping()
		.addBoneAnimation("stem", new Transformation(Transformation.Targets.TRANSLATE,
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.5F, -0.5F, -0.5F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5833F, AnimationHelper.createTranslationalVector(0.1F, 0.0F, -0.1F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.75F, AnimationHelper.createTranslationalVector(-0.1F, 0.0F, 0.1F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.25F,AnimationHelper.createTranslationalVector(-1.0F, -0.5F, 1.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.75F, AnimationHelper.createTranslationalVector(-0.1F, 0.0F, 0.1F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.8333F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.9167F, AnimationHelper.createTranslationalVector(0.1F, 0.0F, -0.1F), Transformation.Interpolations.LINEAR),
            new Keyframe(2.5F, AnimationHelper.createTranslationalVector(0.5F, -0.5F, -0.5F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("head", new Transformation(Transformation.Targets.TRANSLATE,
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.5F, -0.5F, -0.5F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5833F, AnimationHelper.createTranslationalVector(0.05F, 0.0F, -0.05F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.75F, AnimationHelper.createTranslationalVector(-0.05F, 0.0F, 0.05F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(-0.5F, -0.5F, 0.5F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.75F, AnimationHelper.createTranslationalVector(-0.05F, 0.0F, 0.05F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.8333F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.9167F, AnimationHelper.createTranslationalVector(0.05F, 0.0F, -0.05F), Transformation.Interpolations.LINEAR),
            new Keyframe(2.5F, AnimationHelper.createTranslationalVector(0.5F, -0.5F, -0.5F), Transformation.Interpolations.LINEAR)
            ))
            .build();

    public static final Animation ANIM_PEASHOOTER_SHOOT = Animation.Builder.create(0.75F)
            .addBoneAnimation("head", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5833F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("head", new Transformation(Transformation.Targets.SCALE,
                    new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.2917F, AnimationHelper.createScalingVector(0.9F, 1.05F, 0.8F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5833F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
            ))
            .build();
}
