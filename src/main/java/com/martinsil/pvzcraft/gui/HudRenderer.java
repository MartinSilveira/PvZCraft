package com.martinsil.pvzcraft.gui;

import com.martinsil.pvzcraft.adventure.LevelManager;
import com.martinsil.pvzcraft.item.PlantItem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import static com.martinsil.pvzcraft.util.Constants.*;

public class HudRenderer {

    private static MinecraftClient client;
    private static DrawContext context;
    private static RenderTickCounter tickDelta;

    public static void displayOverlays() {
        HudRenderCallback.EVENT.register((drawContext, tickD) -> {
            client = MinecraftClient.getInstance();
            if (client.player == null) return;

            context = drawContext;
            tickDelta = tickD;

            displayHotbarOverlay();
            displaySunOverlay();
            displayLevelOverlay();
            displayBeginLevelOverlay();
            displayWaveOverlay();

            displayPerformanceInfo();

            /* this one might have a few layers to it
             * the base which is permanent
             * the flags that are rendered depending on the level being played
             * the green bar that expands as the level progresses
             */
            //displayLevelProgressOverlay();
        });
    }

    private static void displayHotbarOverlay() {
        PlayerEntity player = client.player;

        // Everything is relative to the hotbar so we just need to change this to move/scale everything
        int hotbarX = 6;
        int hotbarY = 0;
        float scale = 1.92F;

        // Start transforming the screen
        context.getMatrices().push();
        context.getMatrices().translate(hotbarX, hotbarY, 0); // Move the starting point
        context.getMatrices().scale(scale, scale, 1.0F);      // Scale everything

        // Hotbar dimensions
        int hbWidth = 139;
        int hbHeight = 23;

        // Sun Icon dimensions
        int sunDim = 10; // only need 1 variable since the sun icon is always square
        int sunX = 8;
        int sunY = 5;

        // Seed Packet Dimensions
        int slotWidth = 15;
        int slotHeight = 17;
        int slotStartX = 28;
        int slotY = 2;

        int slotSpacing = slotWidth + 3; // pixels between each seed slot

        // Draw Hotbar Background
        context.drawTexture(HOTBAR_TEX, 0, 0, 0, 0, hbWidth, hbHeight, hbWidth, hbHeight);

        // Draw Sun Icon
        context.drawTexture(SUN_TEX, sunX, sunY, 0, 0, sunDim, sunDim, sunDim, sunDim);

        // How many seed slots the level being played should allow
        int maxSlots = 6;
        if (LevelManager.currentLevelData != null && LevelManager.currentLevelData.plants != null) {
            maxSlots = LevelManager.currentLevelData.plants.maxSelections;
        }

        for (int i = 0; i < maxSlots; i++) {
            int slotX = slotStartX + (i * slotSpacing);

            ItemStack stack = player.getInventory().getStack(i);

            if (!stack.isEmpty()) {
                // Draw Seed Packet Background
                context.drawTexture(SEED_PACKET_TEXTURE, slotX, slotY, 0, 0, slotWidth, slotHeight, slotWidth, slotHeight);

                // Logic
                Item item = stack.getItem();
                String plantId = Registries.ITEM.getId(item).toString();
                boolean canAfford = LevelManager.currentSun >= PlantItem.getPlantCost(plantId);
                float cooldownProgress = player.getItemCooldownManager().getCooldownProgress(item, tickDelta.getTickDelta(true));
                boolean onCooldown = cooldownProgress > 0.0F;

                // Define the box for shading
                int shadeX2 = slotX + slotWidth;
                int shadeY2 = slotY + slotHeight;

                // Unaffordable Tone
                if (!canAfford && !onCooldown) {
                    context.fill(slotX, slotY, shadeX2, shadeY2, 0x80000000);
                }

                // Cooldown Tone
                if (onCooldown) {
                    // Base Darken
                    context.fill(slotX, slotY, shadeX2, shadeY2, 0x80000000);

                    // Wipe effect that moves down as the cooldown decreases
                    int wipeHeight = (int) (slotHeight * cooldownProgress);
                    context.fill(slotX, slotY, shadeX2, slotY + wipeHeight, 0xAA000000);
                }
            }

            // Draw Highlight Box
            if (player.getInventory().selectedSlot == i) {
                context.drawTexture(HIGHLIGHT_TEX, slotX, slotY, 0, 0, slotWidth, slotHeight, slotWidth, slotHeight);
            }
        }

        // Reset the screen so the rest draws normally
        context.getMatrices().pop();

        for (int i = 0; i < maxSlots; i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (!stack.isEmpty()) {

                // Manually apply the scale for the items
                int scaledItemX = (int) (hotbarX + ((slotStartX + (i * slotSpacing)) * scale));
                int scaledItemY = (int) (hotbarY + (slotY * scale));

                scaledItemX += 7;
                scaledItemY += 6;

                context.drawItem(stack, scaledItemX, scaledItemY);
            }
        }
    }


    private static void displaySunOverlay() {
        String currentSunText = String.valueOf(LevelManager.currentSun);

        int centerX = 32;
        int centerY = 35;

        // Calculate the width of the text to find the offset
        int textWidth = client.textRenderer.getWidth(currentSunText);

        // Calculate center position
        float renderedX = centerX - (textWidth / 2f);

        context.drawText(client.textRenderer, currentSunText, (int) renderedX, centerY, 0x000000, false);
    }

    private static void displayLevelOverlay() {
        // Define the texture width and height (must match the ratio of the original texture)
        int texWidth = 24;
        int texHeight = 12;

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        // Calculate where to render the texture
        int x = screenWidth - texWidth - 140;
        int y = screenHeight - texHeight - 1;

        context.drawTexture(LEVEL_TEXTURE, x, y, 0, 0, texWidth, texHeight, texWidth, texHeight);
    }

    private static void displayBeginLevelOverlay() {
        if (LevelManager.readyTexTimer > 0)
            selectAndDrawTexture(getAlpha(LevelManager.readyTexTimer, READY_TEXT_DURATION),
                    190, 34, READY_TEXTURE);

        else if (LevelManager.setTexTimer > 0 && LevelManager.setTexTimer <= SET_TEXT_DURATION)
            selectAndDrawTexture(getAlpha(LevelManager.setTexTimer, SET_TEXT_DURATION),
                    101, 34, SET_TEXTURE);

        else if (LevelManager.plantTexTimer > 0 && LevelManager.plantTexTimer <= PLANT_TEXT_DURATION)
            selectAndDrawTexture(getAlpha(LevelManager.plantTexTimer, PLANT_TEXT_DURATION),
                    138, 34, PLANT_TEXTURE);
    }

    private static void displayWaveOverlay() {
        if (LevelManager.hugeWaveTimer > 0)
            selectAndDrawTexture(getAlpha(LevelManager.hugeWaveTimer, HUGE_WAVE_TEXT_DURATION),
                    420, 17, HUGE_WAVE_TEXTURE);

        if (LevelManager.currentWave != null && LevelManager.currentWave.hugeWave && LevelManager.currentWave.finalWave)
            if (LevelManager.finalWaveTimer > 0 && LevelManager.finalWaveTimer <= FINAL_WAVE_TEXT_DURATION - HUGE_WAVE_TEXT_DURATION)
                selectAndDrawTexture(getAlpha(LevelManager.finalWaveTimer, FINAL_WAVE_TEXT_DURATION),
                        230, 34, FINAL_WAVE_TEXTURE);
    }


    private static void displayPerformanceInfo() {
        // Get FPS
        String fpsText = client.getCurrentFps() + " FPS";

        // Get RAM (Convert bytes to Megabytes)
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        long usedMemory = (totalMemory - freeMemory) / 1024L / 1024L;
        long allocatedMemory = maxMemory / 1024L / 1024L;
        String ramText = "RAM: " + usedMemory + "MB / " + allocatedMemory + "MB";

        // Get MSPT (Server Lag)
        String msptText = "MSPT: 0.0";
        int msptColor = 0x55FF55; // Green
        float mspt = 0.0F;
        if (client.getServer() != null) {
            mspt = client.getServer().getAverageTickTime(); // Get the server performance
            if (mspt > 50.0F) msptColor = 0xFF5555; // Red
            else if (mspt > 40.0F) msptColor = 0xFFFF55; // Yellow
            msptText = "MSPT: " + String.format("%.1f", mspt);
        }

        String tpsText = "TPS: 20.0";
        int tpsColor = 0x55FF55; // Green
        if (client.getServer() != null) {
            float tps = 1000.0F / Math.max(50.0F, mspt);
            if (tps < 15.0F) tpsColor = 0xFF5555; // Red
            else if (tps < 17.5F) tpsColor = 0xFFFF55; // Yellow
            tpsText = "TPS: " + String.format("%.1f", Math.min(20.0F, tps));
        }

        // Draw the text in the top right
        int screenWidth = client.getWindow().getScaledWidth();
        int fontHeight = client.textRenderer.fontHeight;

        int fpsX = screenWidth - client.textRenderer.getWidth(fpsText) - 5;
        int ramX = screenWidth - client.textRenderer.getWidth(ramText) - 5;
        int msptX = screenWidth - client.textRenderer.getWidth(msptText) - 5;
        int tpsX = screenWidth - client.textRenderer.getWidth(tpsText) - 5;


        // Draw them stacked on top of each other
        context.drawText(client.textRenderer, fpsText, fpsX, 5, 0xFFFFFF, true);
        context.drawText(client.textRenderer, ramText, ramX, 5 + fontHeight + 2, 0xFFFFFF, true);
        context.drawText(client.textRenderer, msptText, msptX, 5 + (fontHeight * 2) + 4, msptColor, true);
        context.drawText(client.textRenderer, tpsText, tpsX, 5 + (fontHeight * 3) + 6, tpsColor, true);
    }


    private static void selectAndDrawTexture(float alpha, int texWidth, int texHeight, Identifier texToDisplay) {
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        // Center the text
        int x = (screenWidth - texWidth) / 2;
        int y = (screenHeight - texHeight) / 2;

        context.setShaderColor(1.0F, 1.0F, 1.0F, alpha);

        context.drawTexture(texToDisplay, x, y, 0, 0, texWidth, texHeight, texWidth, texHeight);

        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }


    private static float getAlpha(int currentTimer, int maxTimer) {
        float timer = currentTimer - tickDelta.getTickDelta(true);
        float alpha = 1.0F; // Default is fully visible

        // We want the fade to take up exactly 20% of the total time or minimum 5 ticks
        float fadeDuration = Math.max(maxTimer * 0.20F, 5.0F);

        // Fading in
        if (timer > (maxTimer - fadeDuration))
            alpha = (maxTimer - timer) / fadeDuration;

        // Fading out
        else if (timer < fadeDuration)
            alpha = timer / fadeDuration;

        // Clamp the value between 0.0 and 1.0
        return Math.clamp(alpha, 0.0F, 1.0F);
    }
}
