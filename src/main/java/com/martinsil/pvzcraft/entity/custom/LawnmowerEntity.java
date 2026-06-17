package com.martinsil.pvzcraft.entity.custom;

import com.martinsil.pvzcraft.entity.PvZZombieEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

import static com.martinsil.pvzcraft.util.Constants.*;

public class LawnmowerEntity extends MobEntity {
    private boolean isTriggered = false;

    public LawnmowerEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D);
    }

    @Override
    public void tick() {
        super.tick();

        this.setYaw(NORTH * LAWN_MAP_DIR);
        this.setBodyYaw(NORTH * LAWN_MAP_DIR);
        this.setHeadYaw(NORTH * LAWN_MAP_DIR);

        if (!isTriggered) { // Wait for Zombie to touch it
            Box hitbox = this.getBoundingBox();
            List<PvZZombieEntity> zombies = this.getWorld().getEntitiesByClass(PvZZombieEntity.class, hitbox, Entity::isAlive);

            if (!zombies.isEmpty()) {
                this.isTriggered = true;
            }
        } else { // If a Zombie touches it

            // Get all zombies that are relatively close to the Lawnmower
            Box searchBox = this.getBoundingBox();
            List<PvZZombieEntity> nearbyZombies = this.getWorld().getEntitiesByClass(PvZZombieEntity.class, searchBox, Entity::isAlive);

            // Check if the zombies are touching the lawnmower
            for (PvZZombieEntity zombie : nearbyZombies) {

                // Calculate exactly how far away the zombie is on the Z-axis (North/South)
                double distanceZ = Math.abs(this.getZ() - zombie.getZ());

                // If the zombie's center is within 0.2 blocks of the lawnmower's center, we kill it
                if (distanceZ <= 0.2D) {
                    zombie.damage(this.getDamageSources().mobAttack(this), 50000.0F);
                }
            }

            this.setVelocity(0.0D, this.getVelocity().y, -0.3D);

            // Delete the lawnmower when it goes off the lawn + 11 blocks ahead
            if (this.getZ() < LAWN_BORDER_Z[0] - 11) {
                this.discard();
            }
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        // Can't take damage from anything
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushAway(Entity entity) {
        // Does not push other entities away while sitting still
    }

    @Override
    public boolean cannotDespawn() {
        // Prevents Minecraft from despawning it naturally
        return true;
    }
}
