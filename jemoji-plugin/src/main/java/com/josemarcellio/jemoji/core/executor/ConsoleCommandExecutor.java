package com.josemarcellio.jemoji.core.executor;

import com.josemarcellio.jemoji.common.executor.Executor;
import com.josemarcellio.jemoji.core.util.Utility;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getServer;

public class ConsoleCommandExecutor implements Executor {

    @Override
    public String getExecutor() {
        return "console-command";
    }

    @Override
    public void execute(JavaPlugin javaPlugin, Player player, String string) {
        getServer().getScheduler().scheduleSyncDelayedTask(javaPlugin, () -> getServer().dispatchCommand( getServer().getConsoleSender(), Utility.getColor(string)) );
    }
}
