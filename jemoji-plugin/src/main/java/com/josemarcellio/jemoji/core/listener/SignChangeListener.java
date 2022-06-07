package com.josemarcellio.jemoji.core.listener;

import com.josemarcellio.jemoji.core.JEmoji;
import com.josemarcellio.jemoji.core.util.Utility;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    private final JEmoji plugin;

    public SignChangeListener(JEmoji plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer ();
        FileConfiguration file = plugin.getConfig ();
        for (String string : file.getConfigurationSection ( "emoji" ).getKeys ( false )) {
            String emoji = file.getString ( "emoji." + string + ".set-emoji" );
            String permission = file.getString ( "emoji." + string + ".permission" );
            if (file.getBoolean ( "clear-color-after-emoji" )) {
                emoji += file.getString("clear-color-symbol");
            }
            for (int i = 0; i < event.getLines().length; i++) {
                String message = Utility.getColor ( event.getLine ( i ).replace ( string, emoji ) );
                if (event.getLine ( i ).contains ( string )) {
                    if (player.hasPermission ( "jemoji.*" ) || player.hasPermission ( permission ) || permission.equalsIgnoreCase ( "none" )) {
                        event.setLine ( i, message );
                    }
                }
            }
        }
    }
}