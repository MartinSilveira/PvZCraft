package com.martinsil.pvzcraft.adventure;

import java.util.List;

public class SpawnProfileData {
    public String id;
    public List<ZombiePool> zombies;

    public static class ZombiePool {
        public String zombieId;
        public int prob;
        public int minBudget;
        public int maxBudget;
        public int minWave;
        public int maxWave;
        public int cost;
    }
}