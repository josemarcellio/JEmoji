package com.josemarcellio.jemoji.core.listener;

import com.josemarcellio.jemoji.core.util.Utility;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class PlayerChatListener implements Listener {

    private final JavaPlugin plugin;

    public PlayerChatListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer ();
        FileConfiguration file = plugin.getConfig ();
        for (String string : file.getConfigurationSection ( "emoji" ).getKeys ( false )) {
            String emoji = file.getString ( "emoji." + string + ".set-emoji" );
            String permission = file.getString ( "emoji." + string + ".permission" );
            List<String> world = file.getStringList("emoji." + string + ".disable-world");
            if (file.getBoolean ( "clear-color-after-emoji" )) {
                emoji += file.getString("clear-color-symbol");
            }
            String message = Utility.getColor ( event.getMessage ().replace ( string, emoji ) );
            if (event.getMessage ().contains ( string ) && !world.contains(player.getWorld().getName())) {
                if (player.hasPermission ( "jemoji.*" ) || player.hasPermission ( permission ) || permission.equalsIgnoreCase ( "none" )) {
                    event.setMessage ( message );
                }
            }
        }
    }
}