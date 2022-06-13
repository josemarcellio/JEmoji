package com.josemarcellio.jemoji.core.util;

import org.bukkit.Bukkit;

public class ServerUtil {

    public static Byte getVersion() {
        return Byte.parseByte( Bukkit.getServer().getClass().getName().split("\\.")[3].split("_")[1]);
    }

    public static boolean getPluginEnabled (String string) {
        return Bukkit.getServer ().getPluginManager ().isPluginEnabled ( string );
    }
}
