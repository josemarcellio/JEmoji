package com.josemarcellio.jemoji.common.executor;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface Executor {

    String getExecutor();

    void execute(JavaPlugin javaPlugin, Player player, String string);
}
