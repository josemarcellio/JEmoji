package com.josemarcellio.jemoji.core.util;

import org.bukkit.Bukkit;

public class VersionUtil {

    public static boolean isLegacy() {
        return Byte.parseByte( Bukkit.getServer().getClass().getName().split("\\.")[3].split("_")[1]) < 16;
    }
}
