package com.martinsil.pvzcraft;

import com.martinsil.pvzcraft.adventure.LevelManager;
import com.martinsil.pvzcraft.entity.client.*;
import com.martinsil.pvzcraft.gui.HudRenderer;
import com.martinsil.pvzcraft.menu.MenuEditor;
import com.martinsil.pvzcraft.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

import javax.swing.text.html.parser.Entity;

public class PvZCraftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerEntities();

        MenuEditor.changeMainMenu();

        HudRenderer.displayOverlays();

        manageClientTickEvents();
    }

    private void registerEntities() {
        EntityModelLayerRegistry.registerModelLayer(PeashooterModel.PEASHOOTER, PeashooterModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.PEASHOOTER, PeashooterRenderer::new);

        EntityRendererRegistry.register(ModEntities.PEA, FlyingItemEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(RegularZombieModel.REGULAR_ZOMBIE, RegularZombieModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.REGULAR_ZOMBIE, RegularZombieRenderer::new);

        EntityRendererRegistry.register(ModEntities.SUN_ENTITY, SunRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(LawnmowerModel.LAWNMOWER, LawnmowerModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.LAWNMOWER, LawnmowerRenderer::new);
    }

    private void manageClientTickEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                int maxAllowed = 6; // Default fallback
                if (LevelManager.currentLevelData != null && LevelManager.currentLevelData.plants != null) {
                    maxAllowed = LevelManager.currentLevelData.plants.maxSelections;
                }

                int selected = client.player.getInventory().selectedSlot;

                // Prevent selecting empty slots or going beyond maxAllowed
                if (selected == 8) { // Minecraft wraps -1 to 8 internally
                    // If the player scrolled backwards from 0, go to the last allowed slot
                    client.player.getInventory().selectedSlot = maxAllowed - 1;
                } else if (selected >= maxAllowed) {
                    // If the player scrolled past the allowed slots, go back to 0
                    client.player.getInventory().selectedSlot = 0;
                }
            }
        });
    }
}
