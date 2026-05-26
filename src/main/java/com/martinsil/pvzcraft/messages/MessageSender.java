package com.martinsil.pvzcraft.messages;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public class MessageSender {

    public static void broadcast(ServerWorld world, String message) {
        for (ServerPlayerEntity player : world.getPlayers()) {
            player.sendMessage(Text.literal(message), false);
        }
    }
}
