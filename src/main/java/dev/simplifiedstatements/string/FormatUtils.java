package dev.simplifiedstatements.string;

import org.bukkit.ChatColor;

public class FormatUtils {

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
