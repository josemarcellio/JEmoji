package com.josemarcellio.jemoji.core.executor;

import com.josemarcellio.jemoji.common.executor.Executor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CloseInventoryExecutor implements Executor {
    @Override
    public String getExecutor() {
        return "close-inventory";
    }

    @Override
    public void execute(JavaPlugin javaPlugin, Player player, String string) {
        player.closeInventory();
    }
}
