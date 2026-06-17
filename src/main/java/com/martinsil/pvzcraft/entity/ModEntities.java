package com.martinsil.pvzcraft.entity;

import com.martinsil.pvzcraft.PvZCraft;
import com.martinsil.pvzcraft.entity.custom.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<PeashooterEntity> PEASHOOTER = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(PvZCraft.MOD_ID, "peashooter"),
            EntityType.Builder.create(PeashooterEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1.0F, 0.9F).build());

    public static final EntityType<PeaEntity> PEA = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(PvZCraft.MOD_ID, "pea"),
            EntityType.Builder.<PeaEntity>create(PeaEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5F, 0.5F).build());

    public static final EntityType<RegularZombieEntity> REGULAR_ZOMBIE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(PvZCraft.MOD_ID, "regular_zombie"),
            EntityType.Builder.create(RegularZombieEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.9F, 1.7F).build());

    public static final EntityType<SunEntity> SUN_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(PvZCraft.MOD_ID, "sun_entity"),
            EntityType.Builder.create(SunEntity::new, SpawnGroup.MISC)
                    .dimensions(0.9F, 0.9F).build());

    public static final EntityType<LawnmowerEntity> LAWNMOWER = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(PvZCraft.MOD_ID, "lawnmower"),
            EntityType.Builder.create(LawnmowerEntity::new, SpawnGroup.MISC)
                    .dimensions(0.7F, 0.7F).build());


    public static void registerModEntities() {
        PvZCraft.LOGGER.info("Registering Mod Entities for " + PvZCraft.MOD_ID);
    }
}
