package com.martinsil.pvzcraft.menu;

import com.martinsil.pvzcraft.adventure.LevelManager;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class MenuEditor {
    public static void changeMainMenu() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof TitleScreen titleScreen) {

                List<ButtonWidget> buttonsToAdd = new ArrayList<>();

                String singlePlayerText = Text.translatable("menu.singleplayer").getString();
                String multiPlayerText = Text.translatable("menu.multiplayer").getString();
                String realmsText = Text.translatable("menu.online").getString();
                String optionsText = Text.translatable("menu.options").getString();
                String quitText = Text.translatable("menu.quit").getString();

                // Hide all the default buttons and create new buttons matching PvZ's
                for (var widget : Screens.getButtons(screen)) {
                    if (widget instanceof ButtonWidget button) {
                        String currentText = button.getMessage().getString();

                        if (currentText.equals(singlePlayerText)) {
                            ButtonWidget newButton = ButtonWidget.builder(Text.literal("Adventure Level 1-1"), currButton -> {
                                        LevelManager.isAdventureMode = true;
                                        client.createIntegratedServerLoader().start("Dev World", () -> client.setScreen(titleScreen));
                                    }).dimensions(button.getX(), button.getY() - 15, button.getWidth(), button.getHeight())
                                    .build();

                            hideButton(button);
                            buttonsToAdd.add(newButton);
                        }

                        else if (currentText.equals(multiPlayerText)) {
                            ButtonWidget newButton = ButtonWidget.builder(Text.literal("Mini-Games"), currButton -> {
                                        // Does nothing for now
                                    }).dimensions(button.getX(), button.getY() - 15, button.getWidth(), button.getHeight())
                                    .build();

                            hideButton(button);
                            buttonsToAdd.add(newButton);
                        }

                        else if (currentText.equals(realmsText)) {
                            ButtonWidget newButton1 = ButtonWidget.builder(Text.literal("Puzzle"), currButton -> {
                                        // Does nothing for now
                                    }).dimensions(button.getX(), button.getY() - 14, button.getWidth(), button.getHeight())
                                    .build();

                            ButtonWidget newButton2 = ButtonWidget.builder(Text.literal("Survival"), currButton -> {
                                        client.createIntegratedServerLoader().start("Dev World", () -> client.setScreen(titleScreen));
                                    }).dimensions(button.getX(), button.getY() + 11, button.getWidth(), button.getHeight())
                                    .build();

                            buttonsToAdd.add(newButton1);
                            buttonsToAdd.add(newButton2);

                            hideButton(button);
                        }

                        else if (currentText.equals(optionsText)) {
                            button.setMessage(Text.literal("Options"));
                        }

                        else if (currentText.equals(quitText)) {
                            button.setMessage(Text.literal("Quit"));
                        }

                        else {
                            hideButton(button);
                        }
                    }
                }

                // Add all the new buttons to the screen
                Screens.getButtons(screen).addAll(buttonsToAdd);
            }
        });
    }

    private static void hideButton(ButtonWidget button) {
        button.visible = false;
        button.active = false;
    }
}
