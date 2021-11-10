package me.astelics.staffcore.utils;

import org.bukkit.ChatColor;

public class Colour {
    public static String colour(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
