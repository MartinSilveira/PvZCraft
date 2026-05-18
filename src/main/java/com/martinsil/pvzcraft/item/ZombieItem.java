package com.martinsil.pvzcraft.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ZombieItem extends Item {
    // Type of zombie the item is supposed to spawn
    private final EntityType<?> entityType;

    public ZombieItem(EntityType<?> entityType, Settings settings) {
        super(settings);
        this.entityType = entityType;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        if (!(world instanceof ServerWorld)) {
            return ActionResult.SUCCESS;
        }

        BlockPos pos = context.getBlockPos().up();

        // Make the zombie spawn in the middle of a 2x2 square
        double spawnX = Math.floor(pos.getX() / 2.0) * 2.0;
        double spawnY = pos.getY();
        double spawnZ = Math.floor(pos.getZ() / 2.0) * 2.0;

        Entity entity = this.entityType.create(world);
        if (entity != null) {
            // Set its spawn position and make it face South
            entity.setYaw(0.0F);
            entity.setBodyYaw(0.0F);
            entity.setHeadYaw(0.0F);
            entity.refreshPositionAndAngles(spawnX, spawnY, spawnZ, entity.getYaw() , 0.0F);

            world.spawnEntity(entity);

            world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, pos);
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }
}
