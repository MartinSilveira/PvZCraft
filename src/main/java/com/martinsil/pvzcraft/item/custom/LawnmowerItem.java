package com.martinsil.pvzcraft.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import com.martinsil.pvzcraft.entity.ModEntities;

import static com.martinsil.pvzcraft.util.Constants.*;

public class LawnmowerItem extends Item {
    public LawnmowerItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        if (!(world instanceof ServerWorld serverWorld)) {
            return ActionResult.SUCCESS;
        }

        BlockPos pos = context.getBlockPos().up();

        double spawnX = Math.round(pos.getX() / 2.0f) * 2.0;
        double spawnY = pos.getY();
        double spawnZ = Math.round(pos.getZ() / 2.0f) * 2.0;

        var entity = ModEntities.LAWNMOWER.create(serverWorld);

        if (entity != null) {
            float directionToFace = NORTH * LAWN_MAP_DIR;

            entity.setYaw(directionToFace);
            entity.setBodyYaw(directionToFace);
            entity.setHeadYaw(directionToFace);
            entity.refreshPositionAndAngles(spawnX, spawnY, spawnZ, entity.getYaw(), 0.0F);

            serverWorld.spawnEntity(entity);

            if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                context.getStack().decrement(1);
            }

            world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, pos);
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }
}