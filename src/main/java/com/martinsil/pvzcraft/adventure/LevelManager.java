package com.martinsil.pvzcraft.adventure;

import com.martinsil.pvzcraft.entity.ModEntities;
import com.martinsil.pvzcraft.entity.custom.LawnmowerEntity;
import com.martinsil.pvzcraft.entity.custom.SunEntity;
import com.martinsil.pvzcraft.item.ModItems;
import com.martinsil.pvzcraft.messages.MessageSender;
import com.martinsil.pvzcraft.util.PlantConstants;
import com.martinsil.pvzcraft.util.PvZZombieConstants;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.martinsil.pvzcraft.map.MapManager.setupMapForLevel;
import static com.martinsil.pvzcraft.util.Constants.*;

public class LevelManager {
    public enum State { IDLE, PLAYING, WON, LOST }

    public static State currentState = State.IDLE;
    public static boolean isAdventureMode = false;
    private static ServerWorld world = null;

    public static LevelData currentLevelData;

    public static LevelData.Wave currentWave;
    private static final List<String> zombieQueue = new ArrayList<>();
    public static int zombiesAlive = 0;

    private static int startDelayTimer = START_DELAY;

    // Timer that keeps track of when to spawn the next zombie
    private static int nextSpawnTimer = FIRST_ZOMBIE_DELAY;

    // Timers for text overlays
    public static int hugeWaveTimer = 0;
    public static int finalWaveTimer = 0;
    public static int readyTexTimer = 0;
    public static int setTexTimer = 0;
    public static int plantTexTimer = 0;

    // Economy
    public static int currentSun = 0;
    private static int sunDropTimer = 0;

    public static void startLevel(LevelData level) {
        currentLevelData = level;
        currentWave = currentLevelData.waves.getFirst();
        currentSun = currentLevelData.economy.startingSun;
        sunDropTimer = currentLevelData.economy.sunDropIntervalTicks;
        currentState = State.PLAYING;

        // in the future make it so that this only gets called for "special" levels
        // such as 1-1 through 1-5 that have a different lawn
        // otherwise this is useless
        setupMapForLevel(world, currentLevelData.level_id);
        spawnLawnmowers();
    }

    public static void tick() {
        if (currentState != State.PLAYING)
            return;

        startDelayTimer--;
        if (startDelayTimer == 0)
            startWave();
        else if (startDelayTimer > 0)
            return;

        decreaseTimers();

        checkSpawnSun();

        spawnZombiesInQueue();

        startNextWaveIfReady();

        checkWinCondition();
    }

    private static void decreaseTimers() {
        if (!zombieQueue.isEmpty())
            nextSpawnTimer--;

        if (finalWaveTimer > 0)
            finalWaveTimer--;

        if (hugeWaveTimer > 0)
            hugeWaveTimer--;

        if (readyTexTimer > 0)
            readyTexTimer--;

        if (setTexTimer > 0)
            setTexTimer--;

        if (plantTexTimer > 0)
            plantTexTimer--;

        if (currentLevelData.economy.sunDropIntervalTicks > 0)
            sunDropTimer--;
    }

    private static void spawnZombiesInQueue() {
        if (nextSpawnTimer <= 0) {
            Identifier zombieId = Identifier.of(zombieQueue.removeFirst());
            spawnZombie(zombieId);

            if (!currentWave.hugeWave && !currentWave.finalWave)
                // remember to start calculating these dynamically
                // maybe according to the budget of the wave
                nextSpawnTimer = world.random.nextBetween(100, 200);  // 5 to 10 seconds
            else
                nextSpawnTimer = world.random.nextBetween(5, 15);
        }
    }

    private static void spawnZombie(Identifier zombieId) {
        EntityType<?> type = Registries.ENTITY_TYPE.get(zombieId);

        var zombie = type.create(world);
        if (zombie != null) {
            double spawnX;
            if (Objects.equals(currentLevelData.level_id, "1-1"))
                spawnX = LAWN_LANES[2]; // Spawn in middle lane
            else
                spawnX = LAWN_LANES[world.random.nextInt(LAWN_LANES.length)];

            zombie.refreshPositionAndAngles(spawnX, ZOMBIE_LAWN_SPAWN_Y, ZOMBIE_LAWN_SPAWN_Z, SOUTH, 0.0F);

            world.spawnEntity(zombie);
            zombiesAlive++;
        }
    }

    private static void startNextWaveIfReady() {
        if (!currentWave.finalWave && zombieQueue.isEmpty() && zombiesAlive <= 0) {
            currentWave = currentLevelData.waves.get(currentWave.index + 1);
            startWave();
        }
    }

    private static void checkWinCondition() {
        if (currentWave.finalWave && zombieQueue.isEmpty() && zombiesAlive <= 0) {
            currentState = State.WON;
            MessageSender.broadcast(world, "You beat the level! Reward: " + currentLevelData.reward);
        }
    }

    private static void startWave() {
        if (currentWave.index == 0) {
            startDelayTimer = -1;
            readyTexTimer = READY_TEXT_DURATION;
            setTexTimer = SET_TEXT_DURATION;
            plantTexTimer = PLANT_TEXT_DURATION;
        }
        if (currentWave.hugeWave)
            hugeWaveTimer = HUGE_WAVE_TEXT_DURATION;

        if (currentWave.hugeWave && currentWave.finalWave)
            finalWaveTimer = FINAL_WAVE_TEXT_DURATION;

        pickZombiesToSpawn();
    }

    private static void pickZombiesToSpawn() {
        int remainingBudget = currentWave.budget;

        while (remainingBudget > 0) {
            final int currentRemaining = remainingBudget;

            // Filter out the zombies that are impossible to spawn in this wave
            List<LevelData.ZombiePool> validZombies = currentLevelData.zombies.stream()
                    .filter(z -> currentWave.index >= z.minWave && currentWave.index <= z.maxWave)
                    .filter(z -> currentWave.budget >= z.minBudget && currentWave.budget <= z.maxBudget)
                    .filter(z -> getZombieCost(z.zombieId) <= currentRemaining)
                    .toList();

            if (validZombies.isEmpty()) break;

            // Pick the random zombies to spawn this wave
            int totalProb = validZombies.stream().mapToInt(z -> z.prob).sum();
            int randomChoice = world.random.nextInt(totalProb);

            int currentWeight = 0;
            for (LevelData.ZombiePool pool : validZombies) {
                currentWeight += pool.prob;
                if (randomChoice < currentWeight) {
                    zombieQueue.add(pool.zombieId);

                    // Subtract the zombie's cost to the budget
                    remainingBudget -= getZombieCost(pool.zombieId);
                    break;
                }
            }
        }
    }

    public static void onZombieDeath() {
        if (currentState == State.PLAYING)
            zombiesAlive--;
    }

    public static void triggerGameOver() {
        if (currentState == State.PLAYING) {
            currentState = State.LOST;
            MessageSender.broadcast(world, "THE ZOMBIES ATE YOUR BRAINS!");
        }
    }

    private static void spawnLawnmowers() {
        int[] lanesToSpawn = LAWN_LANES;
        if (Objects.equals(currentLevelData.level_id, "1-1"))
            lanesToSpawn = new int[]{LAWN_LANES[2]}; // Spawn them in the middle lane only

        for (int laneX : lanesToSpawn) {
            // Create the Lawnmower
            LawnmowerEntity lawnmower = ModEntities.LAWNMOWER.create(world);

            if (lawnmower != null) {
                // Set the coordinates and rotation
                lawnmower.refreshPositionAndAngles(laneX, LAWN_Y + 1, LAWN_BORDER_Z[1] + 0.2, NORTH * LAWN_MAP_DIR, 0.0F);

                // Spawn it into the world
                world.spawnEntity(lawnmower);
            }
        }
    }

    private static void checkSpawnSun() {
        if (sunDropTimer <= 0) {
            spawnSunFromSky(world);

            // Reset the timer for the next sun
            sunDropTimer = currentLevelData.economy.sunDropIntervalTicks;
        }
    }

    private static void spawnSunFromSky(ServerWorld world) {
        int minX = LAWN_BORDER_X[0] + 1; // make them spawn at most 1 block from the border
        int maxX = LAWN_BORDER_X[1] - 1;
        int minZ = LAWN_BORDER_Z[0] + 1;
        int maxZ = LAWN_BORDER_Z[1] - 1;

        double spawnX = world.random.nextBetween(minX, maxX);
        double spawnZ = world.random.nextBetween(minZ, maxZ);

        // Create the SunEntity
        SunEntity sun = new SunEntity(ModEntities.SUN_ENTITY, world);
        sun.setPosition(spawnX, SUN_SPAWN_Y, spawnZ);

        // Tell it to use the Sun Item texture
        sun.setStack(new ItemStack(ModItems.SUN));

        world.spawnEntity(sun);
    }

    public static void resetLevel() {
        currentState = State.IDLE;
        isAdventureMode = false;

        currentLevelData = null;

        currentWave = null;
        zombiesAlive = 0;
        zombieQueue.clear();

        nextSpawnTimer = FIRST_ZOMBIE_DELAY;
        startDelayTimer = START_DELAY;
        hugeWaveTimer = 0;
        finalWaveTimer = 0;
        readyTexTimer = 0;
        setTexTimer = 0;
        plantTexTimer = 0;

        currentSun = 0;
        sunDropTimer = 0;
    }

    public static void setWorld(ServerWorld overworld) {
        world = overworld;
    }

    private static int getZombieCost(String zombieId) {
        return switch (zombieId) {
            case "pvzcraft:regular_zombie" -> PvZZombieConstants.REGULAR_ZOMBIE_COST;
            default -> 1; // Fallback so the game doesn't crash if there's a typo in the json
        };
    }


}
