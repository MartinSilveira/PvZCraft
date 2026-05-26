package com.martinsil.pvzcraft.item;

import com.martinsil.pvzcraft.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

import static com.martinsil.pvzcraft.util.Constants.*;

public class PlantItem extends Item {
    // Type of plant the item is supposed to spawn
    private final EntityType<?> entityType;


    public PlantItem(EntityType<?> entityType, Settings settings) {
        super(settings);
        this.entityType = entityType;
    }

    private boolean isNotLawn(BlockState state) {
        return !state.isOf(ModBlocks.LAWN_BLOCK_GREEN) &&
                !state.isOf(ModBlocks.LAWN_BLOCK_DARK_GREEN) &&
                !state.isOf(ModBlocks.LAWN_BLOCK_LIGHT_GREEN);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        if (!(world instanceof ServerWorld)) {
            return ActionResult.SUCCESS;
        }

        ItemStack itemStack = context.getStack();
        BlockPos pos = context.getBlockPos().up();

        // Make the plant spawn in the middle of a 2x2 square
        double spawnX = Math.round(pos.getX() / 2.0f) * 2.0;
        double spawnY = pos.getY();
        double spawnZ = Math.round(pos.getZ() / 2.0f) * 2.0;

        // Get the 4 blocks that make up the 2x2 square
        int bX = MathHelper.floor(spawnX);
        int bY = MathHelper.floor(spawnY) - 1; // Look 1 block down (under the plant)
        int bZ = MathHelper.floor(spawnZ);

        BlockState b1 = world.getBlockState(new BlockPos(bX - 1, bY, bZ - 1));
        BlockState b2 = world.getBlockState(new BlockPos(bX, bY, bZ - 1));
        BlockState b3 = world.getBlockState(new BlockPos(bX - 1, bY, bZ));
        BlockState b4 = world.getBlockState(new BlockPos(bX, bY, bZ));

        // If any of those 4 blocks is not lawn blocks, don't place
        if (isNotLawn(b1) || isNotLawn(b2) || isNotLawn(b3) || isNotLawn(b4)) {
            return ActionResult.PASS;
        }

        // Check if there's already a plant on the spot
        Box checkBox = new Box(spawnX - 0.5, spawnY, spawnZ - 0.5, spawnX + 0.5, spawnY + 1.5, spawnZ + 0.5);
        List<AnimalEntity> entitiesInSpot = world.getEntitiesByClass(AnimalEntity.class, checkBox, e -> true);
        if (!entitiesInSpot.isEmpty()) {
            return ActionResult.PASS;
        }

        Entity entity = this.entityType.create(world);
        if (entity != null) {
            // Set its spawn position and make it face North
            float directionToFace = NORTH * LAWN_MAP_DIR;

            entity.setYaw(directionToFace);
            entity.setBodyYaw(directionToFace);
            entity.setHeadYaw(directionToFace);
            entity.refreshPositionAndAngles(spawnX, spawnY, spawnZ, entity.getYaw() , 0.0F);

            world.spawnEntity(entity);

            if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                itemStack.decrement(1);
            }

            world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, pos);
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }
}
