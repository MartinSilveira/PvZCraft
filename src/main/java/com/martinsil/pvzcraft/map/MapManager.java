package com.martinsil.pvzcraft.map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class MapManager {

    public static void clearWorld(ServerWorld world) {
        for (Entity entity : world.iterateEntities())
            if (!(entity instanceof PlayerEntity))
                entity.discard();
    }
}
