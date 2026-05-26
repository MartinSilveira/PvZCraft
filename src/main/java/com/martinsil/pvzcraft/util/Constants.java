package com.martinsil.pvzcraft.util;

import net.minecraft.util.Identifier;

public class Constants {
    // Lawn Constants
    public static final int[] LAWN_LANES = {0, 2, 4, 6, 8};
    public static final int ZOMBIE_LAWN_SPAWN_Y = -60;
    public static final float ZOMBIE_LAWN_SPAWN_Z = -6.5F;
    public static final int[] PLAYER_LAWN_SPAWN_COORDS = {4, -60, 0};
    public static final float LAWN_END = 13.5F;

    // Pool Constants


    // Roof Constants


    // Directions
    public static final float NORTH = 180.0F;
    public static final float SOUTH = 0.0F;

    // Used to invert the directions of Plants and Zombies
    public static final int LAWN_MAP_DIR = 1;
    public static final int ROOF_MAP_DIR = 1;
    public static final int POOL_MAP_DIR = -1;

    // Levels
    public static final int FIRST_ZOMBIE_DELAY = 100;
    public static final int START_DELAY = 100;

    public static final int READY_TEXT_DURATION = 20; // we add READY_TEXT_DURATION + 5 so it starts only after Ready finishes
    public static final int SET_TEXT_DURATION = READY_TEXT_DURATION + 5 + 20;
    public static final int PLANT_TEXT_DURATION = SET_TEXT_DURATION + 5 + 20;

    public static final int HUGE_WAVE_TEXT_DURATION = 100;
    public static final int FINAL_WAVE_TEXT_DURATION = HUGE_WAVE_TEXT_DURATION + 10 + 40;

    // Textures for Overlays
    public static final Identifier HOTBAR_TEX = Identifier.of("pvzcraft", "textures/gui/hotbar.png");
    public static final Identifier HIGHLIGHT_TEX = Identifier.of("pvzcraft", "textures/gui/seed_highlight.png");
    public static final Identifier SUN_TEX = Identifier.of("pvzcraft", "textures/item/sun.png");
    public static final Identifier HUGE_WAVE_TEXTURE = Identifier.of("pvzcraft", "textures/gui/huge_wave.png");
    public static final Identifier FINAL_WAVE_TEXTURE = Identifier.of("pvzcraft", "textures/gui/final_wave.png");
    public static final Identifier READY_TEXTURE = Identifier.of("pvzcraft", "textures/gui/ready.png");
    public static final Identifier SET_TEXTURE = Identifier.of("pvzcraft", "textures/gui/set.png");
    public static final Identifier PLANT_TEXTURE = Identifier.of("pvzcraft", "textures/gui/plant.png");
    public static final Identifier LEVEL_TEXTURE = Identifier.of("pvzcraft", "textures/gui/level.png");
}
