package com.martinsil.pvzcraft.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PeashooterEntity extends AnimalEntity implements RangedAttackMob {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState shootAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public PeashooterEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new ProjectileAttackGoal(this, 0.0D, 30, 25.0F));

        this.targetSelector.add(0, new ActiveTargetGoal<>(this, ZombieEntity.class, 1, true, false, zombie -> {
                                                                                        // Increase reciprocalChance to 5 or 10 if frames get really bad
            // Checks if the zombie in the same lane (1 block wide)
            boolean inSameLane = Math.abs(zombie.getX() - this.getX()) <= 0.5;

            // Checks if the zombie is North of the Peashooter (Negative Z direction)
            boolean isNorth = zombie.getZ() < this.getZ();

            // Checks if the zombie has reached the lawn
            BlockState blockUnderZombie = zombie.getWorld().getBlockState(zombie.getBlockPos().down());
            boolean isOnLawn = blockUnderZombie.isOf(Blocks.GRASS_BLOCK);

            return inSameLane && isNorth && isOnLawn;
        }));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
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
        this.setYaw(180.0F);
        this.setBodyYaw(180.0F);
        this.setHeadYaw(180.0F);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        this.getWorld().sendEntityStatus(this, (byte) 11); // Play shoot animation

        PeaEntity peaEntity = new PeaEntity(this.getWorld(), this);
        peaEntity.setNoGravity(true);

        // Calculate the mouth coordinates
        Vec3d lookDir = this.getRotationVector();
        double spawnX = this.getX();
        double spawnY = this.getY() + 0.6D;
        double spawnZ = this.getZ() - 0.5D;
        peaEntity.setPosition(spawnX, spawnY, spawnZ);

        peaEntity.setVelocity(lookDir.x, 0.0D, lookDir.z, 1F, 0.0F);
        this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.getWorld().spawnEntity(peaEntity);
    }
}
