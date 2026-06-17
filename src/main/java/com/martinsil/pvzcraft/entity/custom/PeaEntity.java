package com.martinsil.pvzcraft.entity.custom;

import com.martinsil.pvzcraft.entity.ModEntities;
import com.martinsil.pvzcraft.entity.PlantProjectileEntity;
import com.martinsil.pvzcraft.entity.PvZZombieEntity;
import com.martinsil.pvzcraft.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

import static com.martinsil.pvzcraft.util.PlantProjectileConstants.PEA_DAMAGE;

public class PeaEntity extends PlantProjectileEntity {
    public PeaEntity(EntityType<? extends PeaEntity> entityType, World world) {
        super(entityType, world);
    }

    public PeaEntity(LivingEntity owner, World world) {
        super(ModEntities.PEA, owner, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() instanceof PvZZombieEntity zombie) {
            zombie.damage(this.getDamageSources().thrown(this, this.getOwner()), PEA_DAMAGE);

            zombie.timeUntilRegen = 0;
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.PEA;
    }
}


