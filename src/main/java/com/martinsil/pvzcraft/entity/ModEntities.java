package com.martinsil.pvzcraft.entity;

import com.martinsil.pvzcraft.PvZCraft;
import com.martinsil.pvzcraft.entity.custom.PeaEntity;
import com.martinsil.pvzcraft.entity.custom.PeashooterEntity;
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
                    .dimensions(0.2F, 0.2F).build());


    public static void registerModEntities() {
        PvZCraft.LOGGER.info("Registering Mod Entities for " + PvZCraft.MOD_ID);
    }
}
