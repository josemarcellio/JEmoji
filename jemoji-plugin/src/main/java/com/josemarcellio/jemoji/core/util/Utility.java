package com.josemarcellio.jemoji.core.util;

import org.bukkit.ChatColor;

public class Utility {

    public static String getColor(String string) {
        return ChatColor.translateAlternateColorCodes ('&', string);
    }
}
