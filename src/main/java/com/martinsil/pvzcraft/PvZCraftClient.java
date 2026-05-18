package com.martinsil.pvzcraft;

import com.martinsil.pvzcraft.entity.ModEntities;
import com.martinsil.pvzcraft.entity.client.PeashooterModel;
import com.martinsil.pvzcraft.entity.client.PeashooterRenderer;
import com.martinsil.pvzcraft.entity.client.RegularZombieModel;
import com.martinsil.pvzcraft.entity.client.RegularZombieRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class PvZCraftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(PeashooterModel.PEASHOOTER, PeashooterModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.PEASHOOTER, PeashooterRenderer::new);

        EntityRendererRegistry.register(ModEntities.PEA, FlyingItemEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(RegularZombieModel.REGULAR_ZOMBIE, RegularZombieModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.REGULAR_ZOMBIE, RegularZombieRenderer::new);

    }
}
