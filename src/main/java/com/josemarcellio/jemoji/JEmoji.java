package com.josemarcellio.jemoji;

import com.josemarcellio.jemoji.core.command.MainCommand;
import com.josemarcellio.jemoji.core.listener.InventoryClickListener;
import com.josemarcellio.jemoji.core.listener.PlayerChatListener;
import com.josemarcellio.jemoji.core.metrics.Metrics;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class JEmoji extends JavaPlugin {

    public static JEmoji instance;
    private final MainCommand mainCommand = new MainCommand();

    public void register(Listener... listeners) {
        Arrays.stream ( listeners ).forEach ( listener -> this.getServer ().getPluginManager ().registerEvents ( listener, this ) );
    }

    @Override
    public void onEnable() {

        getLogger().info("JEmoji by JoseMarcellio");

        new Metrics (this, 15391);

        saveDefaultConfig ();
        instance = this;

        register(new PlayerChatListener (this), new InventoryClickListener (this));

        getCommand(mainCommand.getMainCommand()).setExecutor(mainCommand);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling JEmoji");
    }
}
