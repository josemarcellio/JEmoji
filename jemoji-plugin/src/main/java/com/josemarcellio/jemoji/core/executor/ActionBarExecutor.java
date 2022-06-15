package com.josemarcellio.jemoji.core.executor;

import com.cryptomorin.xseries.messages.ActionBar;
import com.josemarcellio.jemoji.common.executor.Executor;
import com.josemarcellio.jemoji.core.util.Utility;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ActionBarExecutor implements Executor {
    @Override
    public String getExecutor() {
        return "actionbar";
    }

    @Override
    public void execute(JavaPlugin javaPlugin, Player player, String string) {
        ActionBar.sendActionBar (javaPlugin, player, Utility.getColor(string));
    }
}
