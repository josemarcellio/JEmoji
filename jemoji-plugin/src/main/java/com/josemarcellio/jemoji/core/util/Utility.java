package com.josemarcellio.jemoji.core.util;

import com.cryptomorin.xseries.XSound;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utility {

    public static String getColor(String string) {
        if (ServerUtil.getVersion() < 16) {
            return ChatColor.translateAlternateColorCodes ('&', string);
        } else {
            return HexColor.getColor(string);
        }
    }

    public static String getPlaceholder(Player player, String string) {
        if (ServerUtil.getPluginEnabled ( "PlaceholderAPI" )) {
            return PlaceholderAPI.setPlaceholders ( player, getColor ( string ) );
        } else {
            return getColor(string);
        }
    }

    public static void playSound(Player player, String sound) {
        XSound.matchXSound(sound).ifPresent ( xSound -> xSound.play ( player.getLocation (), 1f, 1f ) );
    }
}
