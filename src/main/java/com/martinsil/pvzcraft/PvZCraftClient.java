package com.martinsil.pvzcraft;

import com.martinsil.pvzcraft.gui.HudRenderer;
import com.martinsil.pvzcraft.menu.MenuEditor;
import com.martinsil.pvzcraft.entity.ModEntities;
import com.martinsil.pvzcraft.entity.client.PeashooterModel;
import com.martinsil.pvzcraft.entity.client.PeashooterRenderer;
import com.martinsil.pvzcraft.entity.client.RegularZombieModel;
import com.martinsil.pvzcraft.entity.client.RegularZombieRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

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
    }

    private void manageClientTickEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                // If the player scrolls past slot 5 (the 6th slot), put it back on 0 (1st slot)
                if (client.player.getInventory().selectedSlot > 5) {
                    client.player.getInventory().selectedSlot = 0;
                }
            }
        });
    }
}
