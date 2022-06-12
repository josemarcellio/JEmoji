package com.josemarcellio.jemoji.core.executor;

import com.josemarcellio.jemoji.common.executor.Executor;
import com.josemarcellio.jemoji.core.util.Utility;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MessageExecutor implements Executor {

    @Override
    public String getExecutor() {
        return "message";
    }

    @Override
    public void execute(JavaPlugin javaPlugin, Player player, String string) {
        player.sendMessage( Utility.getColor(string));
    }
}
