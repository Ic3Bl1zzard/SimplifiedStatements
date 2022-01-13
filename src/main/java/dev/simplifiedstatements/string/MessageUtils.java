package dev.simplifiedstatements.string;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageUtils {

    public static void sendFormattedMessage(String text, Player player) {
        if (player != null) {
            player.sendMessage(FormatUtils.color(text));
        } else {
            Bukkit.broadcastMessage(FormatUtils.color(text));
        }
    }


    public static void sendFormattedMessage(String text) {
        sendFormattedMessage(text, null);
    }

    public static void sendConsoleMessage(String text) {
        Bukkit.getConsoleSender().sendMessage(FormatUtils.color(text));
    }
}
