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

import static com.martinsil.pvzcraft.util.Constants.*;

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
        double spawnX = Math.round(pos.getX() / 2.0f) * 2.0;
        double spawnY = pos.getY();
        double spawnZ = Math.round(pos.getZ() / 2.0f) * 2.0;

        Entity entity = this.entityType.create(world);
        if (entity != null) {
            // Set its spawn position and make it face South
            float directionToFace = SOUTH * LAWN_MAP_DIR;

            entity.setYaw(directionToFace);
            entity.setBodyYaw(directionToFace);
            entity.setHeadYaw(directionToFace);
            entity.refreshPositionAndAngles(spawnX, spawnY, spawnZ, entity.getYaw() , 0.0F);

            world.spawnEntity(entity);

            world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, pos);
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }
}
