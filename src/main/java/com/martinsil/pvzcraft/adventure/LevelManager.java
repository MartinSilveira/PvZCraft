package com.martinsil.pvzcraft.adventure;

import com.martinsil.pvzcraft.messages.MessageSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import java.util.ArrayList;
import java.util.List;

import static com.martinsil.pvzcraft.util.Constants.*;

public class LevelManager {
    public enum State { IDLE, PLAYING, WON, LOST }

    public static State currentState = State.IDLE;
    public static boolean isAdventureMode = false;
    private static ServerWorld world = null;

    public static LevelData currentLevelData;
    public static SpawnProfileData currentProfileData;

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

    public static void startLevel(LevelData level, SpawnProfileData profile) {
        currentLevelData = level;
        currentProfileData = profile;
        currentWave = currentLevelData.waves.getFirst();
        currentState = State.PLAYING;
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
            double spawnX = LAWN_LANES[world.random.nextInt(LAWN_LANES.length)];

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

        // Algorithm that decides what zombies to spawn in that wave
        while (remainingBudget > 0) {
            final int currentRemaining = remainingBudget;

            // Filter zombies that are allowed in this wave and it can afford
            List<SpawnProfileData.ZombiePool> validZombies = currentProfileData.zombies.stream()
                    .filter(z -> currentWave.index >= z.minWave && currentWave.index <= z.maxWave) // Check wave limits
                    .filter(z -> currentWave.budget >= z.minBudget && currentWave.budget <= z.maxBudget) // Check wave budget thresholds
                    .filter(z -> z.cost <= currentRemaining) // Check if it can afford it
                    .toList();

            // If nothing fits the remaining budget, stop buying
            if (validZombies.isEmpty()) break;

            // Calculate total probability weight
            int totalProb = validZombies.stream().mapToInt(z -> z.prob).sum();
            int randomChoice = world.random.nextInt(totalProb);

            // Select zombie based on probability
            int currentWeight = 0;
            for (SpawnProfileData.ZombiePool pool : validZombies) {
                currentWeight += pool.prob;
                if (randomChoice < currentWeight) {
                    zombieQueue.add(pool.zombieId); // Add to the spawn queue
                    remainingBudget -= pool.cost;
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

    public static void resetLevel() {
        currentState = State.IDLE;
        isAdventureMode = false;

        currentLevelData = null;
        currentProfileData = null;

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
    }

    public static void setWorld(ServerWorld overworld) {
        world = overworld;
    }
}
