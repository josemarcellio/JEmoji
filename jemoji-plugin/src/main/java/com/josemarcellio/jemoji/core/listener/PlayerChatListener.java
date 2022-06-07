package com.josemarcellio.jemoji.core.listener;

import com.josemarcellio.jemoji.core.JEmoji;
import com.josemarcellio.jemoji.core.util.Utility;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private final JEmoji plugin;

    public PlayerChatListener(JEmoji plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer ();
        FileConfiguration file = plugin.getConfig ();
        for (String string : file.getConfigurationSection ( "emoji" ).getKeys ( false )) {
            String emoji = file.getString ( "emoji." + string + ".set-emoji" );
            String permission = file.getString ( "emoji." + string + ".permission" );
            if (file.getBoolean ( "clear-color-after-emoji" )) {
                emoji += file.getString("clear-color-symbol");
            }
            String message = Utility.getColor ( event.getMessage ().replace ( string, emoji ) );
            if (event.getMessage ().contains ( string )) {
                if (player.hasPermission ( "jemoji.*" ) || player.hasPermission ( permission ) || permission.equalsIgnoreCase ( "none" )) {
                    event.setMessage ( message );
                }
            }
        }
    }
}