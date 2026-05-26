package com.martinsil.pvzcraft;

import com.martinsil.pvzcraft.adventure.LevelData;
import com.martinsil.pvzcraft.adventure.LevelDataLoader;
import com.martinsil.pvzcraft.adventure.LevelManager;
import com.martinsil.pvzcraft.adventure.SpawnProfileData;
import com.martinsil.pvzcraft.block.ModBlocks;
import com.martinsil.pvzcraft.entity.ModEntities;
import com.martinsil.pvzcraft.entity.custom.PeashooterEntity;
import com.martinsil.pvzcraft.entity.custom.RegularZombieEntity;
import com.martinsil.pvzcraft.item.ModItemGroups;
import com.martinsil.pvzcraft.item.ModItems;
import com.martinsil.pvzcraft.map.MapManager;
import static com.martinsil.pvzcraft.util.Constants.*;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PvZCraft implements ModInitializer {
	public static final String MOD_ID = "pvzcraft";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Load the data for all the levels
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new LevelDataLoader());

		registerMod();

		registerModEntities();

		setServerLifecycleEvents();

		manageCommands();

		manageServerTickEvents();

		manageServerPlayConnectionEvents();
	}

	private void registerMod() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();

		ModEntities.registerModEntities();

		ModBlocks.registerModBlocks();
	}

	private void registerModEntities() {
		FabricDefaultAttributeRegistry.register(ModEntities.PEASHOOTER, PeashooterEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.REGULAR_ZOMBIE, RegularZombieEntity.createAttributes());
	}

	private void manageCommands() {

		// Command /pvzstart arbitrarily starts the test level when not in Adventure mode
		CommandRegistrationCallback.EVENT.register((dispatcher,
		                                            registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("pvzstart")
					.executes(context -> {

						// Load the level and profile data
						LevelData level = LevelDataLoader.LOADED_LEVELS.get("1_0");
						if (level != null) {
							SpawnProfileData profile = LevelDataLoader.LOADED_PROFILES.get(level.spawnProfileId);

							// Start that particular level with that profile
							LevelManager.startLevel(level, profile);
						} else {
							LOGGER.info("ERROR: Could not find Test level JSON");
						}
						return 1;
					}));
		});
	}


	private void manageServerTickEvents() {
		ServerTickEvents.END_WORLD_TICK.register(world -> {
			if (world.getRegistryKey() == World.OVERWORLD) {
				LevelManager.tick();
			}
		});
	}


	private void manageServerPlayConnectionEvents() {
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerWorld world = server.getOverworld();
			MapManager.clearWorld(world);

			ServerPlayerEntity player = handler.getPlayer();
			player.teleport(world, PLAYER_LAWN_SPAWN_COORDS[0], PLAYER_LAWN_SPAWN_COORDS[1], PLAYER_LAWN_SPAWN_COORDS[2], NORTH, 0.0F);

			if (LevelManager.isAdventureMode) {
				LevelData level = LevelDataLoader.LOADED_LEVELS.get("1_1");

				if (level != null) {
					SpawnProfileData profile = LevelDataLoader.LOADED_PROFILES.get(level.spawnProfileId);
					LevelManager.startLevel(level, profile);
				} else {
					LOGGER.info("ERROR: Could not find Adventure Level JSON!");
				}
			} else {
				LevelManager.resetLevel();
			}
		});

		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
			// Reset the game when player quits to the Main Menu
			LevelManager.resetLevel();
		});
	}


	private void setServerLifecycleEvents() {
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			var overworld = server.getOverworld();

			if (overworld != null) {
				LevelManager.setWorld(overworld);

				// Turn off the Day and Weather Cycle
				overworld.setTimeOfDay(6000);
				overworld.setWeather(1000000, 0, false, false);
				server.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(false, server);
				server.getGameRules().get(GameRules.DO_WEATHER_CYCLE).set(false, server);

				server.getGameRules().get(GameRules.DO_MOB_SPAWNING).set(false, server);
			}
		});
	}
}