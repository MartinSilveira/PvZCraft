package com.martinsil.pvzcraft.gui;

import com.martinsil.pvzcraft.adventure.LevelManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
            displayLevelOverlay();
            displayBeginLevelOverlay();
            displayWaveOverlay();

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

        // Draw the seed hotbar at the top left
        int hotbarX = 2;
        int hotbarY = 0;
        int hotbarWidth = 188;
        int hotbarHeight = 32;
        context.drawTexture(HOTBAR_TEX, hotbarX, hotbarY, 0, 0, hotbarWidth, hotbarHeight, hotbarWidth, hotbarHeight);

        // Draw the sun icon inside the seed hotbar
        int sunX = hotbarX + 16;
        int sunY = hotbarY + 6;
        int sunWidth = 13;
        int sunHeight = 13;
        context.drawTexture(SUN_TEX, sunX, sunY, 0, 0, sunWidth, sunHeight, sunWidth, sunHeight);

        // Loop through the first 6 slots of the player's inventory
        int maxSlots = 6;
        int slotSpacing = 23; // How many pixels between each slot
        int itemStartX = hotbarX + 42; // Where the first item starts
        int itemStartY = hotbarY + 5;

        for (int i = 0; i < maxSlots; i++) {
            int currentX = itemStartX + (i * slotSpacing);

            // Draw the Item
            ItemStack stack = player.getInventory().getStack(i);
            if (!stack.isEmpty()) {
                context.drawItem(stack, currentX, itemStartY);
            }

            // Draw the Highlight over the selected slot
            if (player.getInventory().selectedSlot == i) {
                context.drawTexture(HIGHLIGHT_TEX, currentX - 2, itemStartY - 2, 0, 0, 20, 21, 20, 21);
            }
        }
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
                    154, 21, READY_TEXTURE);

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
