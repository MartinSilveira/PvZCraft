package com.martinsil.pvzcraft.adventure;

import java.util.List;

public class LevelData {
    public String level_id;
    public String level_name;
    public String type;
    public String map;
    public Economy economy;
    public Plants plants;
    public List<ZombiePool> zombies;
    public List<Wave> waves;
    public String reward;

    public static class Economy {
        public int startingSun;
        public int sunDropIntervalTicks;
    }

    public static class Plants {
        public List<String> disallowed;
        public int maxSelections;
    }

    public static class ZombiePool {
        public String zombieId;
        public int prob;
        public int minWave;
        public int maxWave;
        public int minBudget;
        public int maxBudget;
    }

    public static class Wave {
        public int index;
        public int budget;
        public boolean hugeWave;
        public boolean finalWave;
    }
}
