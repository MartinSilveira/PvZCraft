package com.martinsil.pvzcraft.entity.custom;

import com.martinsil.pvzcraft.block.ModBlocks;
import com.martinsil.pvzcraft.entity.PlantEntity;
import com.martinsil.pvzcraft.entity.PvZZombieEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static com.martinsil.pvzcraft.util.Constants.*;
import static com.martinsil.pvzcraft.util.PlantConstants.*;
import static com.martinsil.pvzcraft.util.PlantProjectileConstants.PEA_SPEED;

public class PeashooterEntity extends PlantEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState shootAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public PeashooterEntity(EntityType<? extends PlantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new Goal() {
            private int cooldown = 5;

            @Override
            public boolean canStart() {
                // Start if we have a target
                return getTarget() != null;
            }

            @Override
            public void start() {
                // When the goal starts, set cooldown to 5 so it fires almost instantly
                this.cooldown = 5;
            }

            @Override
            public void tick() {
                if (getTarget() == null) return;

                if (this.cooldown <= 0) {
                    shoot();
                    this.cooldown = PEASHOOTER_SHOOT_INT; // shoots every 28 ticks
                } else {
                    this.cooldown--;
                }
            }
        });

        this.targetSelector.add(0, new ActiveTargetGoal<>(this, PvZZombieEntity.class, 1, true, false, zombie -> {
                                                                                        // Increase reciprocalChance to 5 or 10 if frames get really bad
            // Checks if the zombie in the same lane
            boolean inSameLane = Math.abs(zombie.getX() - this.getX()) <= 0.5;

            // Checks if the zombie is North of the Peashooter (Negative Z direction)
            boolean isNorth = zombie.getZ() < this.getZ();

            // Checks if the zombie has reached the lawn
            BlockState blockUnderZombie = zombie.getWorld().getBlockState(zombie.getBlockPos().down());
            boolean isOnLawn = (blockUnderZombie.isOf(ModBlocks.LAWN_BLOCK_GREEN) ||
                                blockUnderZombie.isOf(ModBlocks.LAWN_BLOCK_DARK_GREEN) ||
                                blockUnderZombie.isOf(ModBlocks.LAWN_BLOCK_LIGHT_GREEN));

            return inSameLane && isNorth && isOnLawn;
        }));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PEASHOOTER_HEALTH)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D); // decrease this to the min range needed if frames get bad
    }


    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 50; // 2.5 seconds (50 ticks)
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 11) { // If we receive the shoot code
            this.shootAnimationState.stop();
            this.shootAnimationState.start(this.age);
        } else {
            super.handleStatus(status);
        }
    }

    // Look for a better solution for the non-movement maybe
    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }

        // Force the body and head to face North (180.0F)
        float directionToFace = NORTH * LAWN_MAP_DIR;

        this.setYaw(directionToFace);
        this.setBodyYaw(directionToFace);
        this.setHeadYaw(directionToFace);
    }

    public void shoot() {
        this.getWorld().sendEntityStatus(this, (byte) 11); // Play shoot animation

        PeaEntity peaEntity = new PeaEntity(this, this.getWorld());
        peaEntity.setNoGravity(true);

        // Calculate where to shoot the Pea from
        double spawnX = this.getX();
        double spawnY = this.getY() + 0.6D;
        double spawnZ = this.getZ();
        peaEntity.setPosition(spawnX, spawnY, spawnZ);

        Vec3d lookDir = this.getRotationVector();
        peaEntity.setVelocity(lookDir.x, 0.0D, lookDir.z, PEA_SPEED, 0.0F);
        this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.getWorld().spawnEntity(peaEntity);
    }
}
