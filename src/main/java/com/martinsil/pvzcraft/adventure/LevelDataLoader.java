package com.martinsil.pvzcraft.adventure;

import com.google.gson.Gson;
import com.martinsil.pvzcraft.PvZCraft;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class LevelDataLoader implements SimpleSynchronousResourceReloadListener {

    private static final Gson GSON = new Gson();
    public static final Identifier ID = Identifier.of("pvzcraft", "data_loader");

    public static final Map<String, LevelData> LOADED_LEVELS = new HashMap<>();

    @Override
    public Identifier getFabricId() {
        return ID;
    }

    @Override
    public void reload(ResourceManager manager) {

        LOADED_LEVELS.clear();

        // Load Levels
        Map<Identifier, Resource> levelFiles = manager.findResources("levels", id -> id.getPath().endsWith(".json"));
        for (Map.Entry<Identifier, Resource> entry : levelFiles.entrySet()) {
            try (Reader reader = new InputStreamReader(entry.getValue().getInputStream())) {
                LevelData level = GSON.fromJson(reader, LevelData.class);
                LOADED_LEVELS.put(level.level_id, level);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        PvZCraft.LOGGER.info("Loaded {} PvZ Levels!", LOADED_LEVELS.size());
    }
}
