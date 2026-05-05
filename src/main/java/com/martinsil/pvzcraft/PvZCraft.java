package com.martinsil.pvzcraft;

import com.martinsil.pvzcraft.entity.ModEntities;
import com.martinsil.pvzcraft.entity.client.PeashooterModel;
import com.martinsil.pvzcraft.entity.custom.PeashooterEntity;
import com.martinsil.pvzcraft.item.ModItemGroups;
import com.martinsil.pvzcraft.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PvZCraft implements ModInitializer {
	public static final String MOD_ID = "pvzcraft";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();

		ModEntities.registerModEntities();

		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			var overworld = server.getOverworld();
			if (overworld != null) {
				// Turn off the Daylight and Weather Cycle
				overworld.setTimeOfDay(6000);
				overworld.setWeather(1000000, 0, false, false);
				server.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(false, server);
				server.getGameRules().get(GameRules.DO_WEATHER_CYCLE).set(false, server);
			}
		});

		FabricDefaultAttributeRegistry.register(ModEntities.PEASHOOTER, PeashooterEntity.createAttributes());
	}
}