package com.martinsil.pvzcraft.map;

import com.martinsil.pvzcraft.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

import static com.martinsil.pvzcraft.util.Constants.*;

public class MapManager {

    public static void clearWorld(ServerWorld world) {
        // We need this temporary list so that the entities aren't getting deleted
        // from the actual list as the server tries to read it
        List<Entity> toDelete = new ArrayList<>();

        // Get all the entities
        for (Entity entity : world.iterateEntities())
            if (!(entity instanceof PlayerEntity))
                toDelete.add(entity);

        // Delete all of them
        for (Entity entity : toDelete)
            entity.discard();
    }

    public static void setupMapForLevel(ServerWorld world, String levelId) {
        // Draw the pattern with the different grass types on the lawn
        resetLawn(world);

        // only the middle lane has actual lawn
        if (levelId.equals("1-1")) {
            fillLaneWithDirt(world, -1);
            fillLaneWithDirt(world, 1);
            fillLaneWithDirt(world, 5);
            fillLaneWithDirt(world, 7);
        }
    }

    private static void resetLawn(ServerWorld world) {
        int startZ = LAWN_BORDER_Z[0];
        int endZ = LAWN_BORDER_Z[1] - 1;

        // Loop through the 5 lanes
        for (int i = 0; i < LAWN_LANES.length; i++) {
            int laneX = LAWN_LANES[i] - 1;

            // i % 2 == 0 means Lanes 1, 3, 5, otherwise Lanes 2, 4
            boolean isOddLane = (i % 2 == 0);

            for (int z = startZ; z <= endZ; z++) {
                // Calculate which 2x2 tile based on the Z distance
                BlockState stateToPlace = getBlockState(z, startZ, isOddLane);

                // Place the 2 blocks wide for this specific Z row
                for (int x = laneX; x <= laneX + 1; x++)
                    world.setBlockState(new BlockPos(x, LAWN_Y, z), stateToPlace);
            }
        }
    }

    private static BlockState getBlockState(int z, int startZ, boolean isOddLane) {
        int tileZ = Math.abs((z - startZ) / 2);

        BlockState stateToPlace;
        if (isOddLane) {
            // Lane 1, 3, 5: Green -> Dark Green -> Green
            stateToPlace = (tileZ % 2 == 0) ? ModBlocks.LAWN_BLOCK_GREEN.getDefaultState() : ModBlocks.LAWN_BLOCK_DARK_GREEN.getDefaultState();
        } else {
            // Lane 2, 4: Light Green -> Green -> Light Green
            stateToPlace = (tileZ % 2 == 0) ? ModBlocks.LAWN_BLOCK_LIGHT_GREEN.getDefaultState() : ModBlocks.LAWN_BLOCK_GREEN.getDefaultState();
        }
        return stateToPlace;
    }

    private static void fillLaneWithDirt(ServerWorld world, int laneX) {
        int startZ = LAWN_BORDER_Z[0];
        int endZ = LAWN_BORDER_Z[1] - 1;

        for (int x = laneX; x <= laneX + 1; x++) {
            for (int z = startZ; z <= endZ; z++) {

                // 30% chance of placing a dirt block that contains a rock
                BlockState stateToPlace = world.random.nextFloat() < 0.30F
                        ? ModBlocks.DIRT_ROCK_BLOCK.getDefaultState()
                        : ModBlocks.DIRT_BLOCK.getDefaultState();

                world.setBlockState(new BlockPos(x, LAWN_Y, z), stateToPlace);
            }
        }
    }
}
