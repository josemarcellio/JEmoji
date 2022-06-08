package com.josemarcellio.jemoji.core.util;

import com.cryptomorin.xseries.XSound;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utility {

    public static String getColor(String string) {
        if (VersionUtil.getVersion() < 16) {
            return ChatColor.translateAlternateColorCodes ('&', string);
        } else {
            return HexColor.getColor(string);
        }
    }

    public static void playSound(Player player, String sound) {
        XSound.matchXSound(sound).ifPresent ( xSound -> xSound.play ( player.getLocation (), 1f, 1f ) );
    }
}
