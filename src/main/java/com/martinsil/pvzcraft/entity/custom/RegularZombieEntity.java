package com.martinsil.pvzcraft.entity.custom;

import com.martinsil.pvzcraft.entity.PlantEntity;
import com.martinsil.pvzcraft.entity.PvZZombieEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class RegularZombieEntity extends PvZZombieEntity {
    public RegularZombieEntity(EntityType<? extends PvZZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new MeleeAttackGoal(this, 0.0D, false));

        // Target a plant                                                                // increase if frames get bad
        this.targetSelector.add(0, new ActiveTargetGoal<>(this, PlantEntity.class, 5, false, false, plant -> {

            // Checks if the plant is in the same lane
            boolean inSameLane = Math.abs(plant.getX() - this.getX()) <= 0.5;

            // Checks if the plant is close
            boolean isClose = this.distanceTo(plant) <= 1;

            return inSameLane && isClose;
        }));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 60.0D);
    }

    @Override
    public void tick() {
        super.tick();

        /* if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        } */

        // Move toward South until it finds a plant to eat
        if (getTarget() == null) {
            setVelocity(0.0D,  getVelocity().y, 0.03D);
        }

        // Force the body and head to face South
        this.setYaw(0.0F);
        this.setBodyYaw(0.0F);
        this.setHeadYaw(0.0F);
    }
}
