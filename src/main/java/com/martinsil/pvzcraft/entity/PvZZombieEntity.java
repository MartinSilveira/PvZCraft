package com.martinsil.pvzcraft.entity;

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
    public boolean damage(DamageSource source, float amount) {
        boolean wasDamaged = super.damage(source, amount);

        // If the zombie took damage, reset its invincibility shield
        if (wasDamaged) {
            this.timeUntilRegen = 0;
        }

        return wasDamaged;
    }
}
