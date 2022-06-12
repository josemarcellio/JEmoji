package com.josemarcellio.jemoji.core.executor;

import com.josemarcellio.jemoji.common.executor.Executor;
import com.josemarcellio.jemoji.core.util.Utility;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerChatExecutor implements Executor {
    @Override
    public String getExecutor() {
        return "chat";
    }

    @Override
    public void execute(JavaPlugin javaPlugin, Player player, String string) {
        player.chat( Utility.getColor(string));
    }
}
