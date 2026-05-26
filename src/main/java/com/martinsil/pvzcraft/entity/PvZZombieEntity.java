package com.martinsil.pvzcraft.entity;

import com.martinsil.pvzcraft.adventure.LevelManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class PvZZombieEntity extends HostileEntity {
    public PvZZombieEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        if (!this.getWorld().isClient()) {
            LevelManager.onZombieDeath();
        }
    }
}
