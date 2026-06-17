package com.martinsil.pvzcraft.entity.custom;

import com.martinsil.pvzcraft.adventure.LevelManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.sound.SoundEvents;

public class SunEntity extends ItemEntity {

    public SunEntity(EntityType<? extends ItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        // Make the sun fall slowly from sky
        if (!this.isOnGround()) {
            this.setVelocity(0.0D, -0.05D, 0.0D);
        } else {
            // Stops completely when it hits the grass
            this.setVelocity(0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (!this.getWorld().isClient()) {

            // Add 25 sun to the player's count
            LevelManager.currentSun += 25;

            // Play the XP orb sound
            this.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);

            // Delete it from the world instead of putting it on the inventory
            this.discard();
        }
    }
}
