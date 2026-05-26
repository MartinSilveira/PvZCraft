package com.martinsil.pvzcraft.entity.custom;

import com.martinsil.pvzcraft.entity.ModEntities;
import com.martinsil.pvzcraft.entity.PvZZombieEntity;
import com.martinsil.pvzcraft.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class PeaEntity extends ThrownItemEntity {
    public PeaEntity(EntityType<? extends PeaEntity> entityType, World world) {
        super(entityType, world);
    }

    public PeaEntity(World world, LivingEntity owner) {
        super(ModEntities.PEA, owner, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.PEA;
    }

    @Override
    protected boolean canHit(Entity entity) {
        // If the entity is not a Zombie, the Pea ignores it and flies through it
        if (!(entity instanceof PvZZombieEntity)) {
            return false;
        }

        return super.canHit(entity);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() instanceof PvZZombieEntity zombie) {
            int i = 20; // deals 20 damage
            zombie.damage(this.getDamageSources().thrown(this, this.getOwner()), i);

            zombie.timeUntilRegen = 0;
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }
}


