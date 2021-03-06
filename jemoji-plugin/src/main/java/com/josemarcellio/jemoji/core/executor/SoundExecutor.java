package com.josemarcellio.jemoji.core.executor;

import com.cryptomorin.xseries.XSound;
import com.josemarcellio.jemoji.common.executor.Executor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SoundExecutor implements Executor {

    @Override
    public String getExecutor() {
        return "sound";
    }

    @Override
    public void execute(JavaPlugin javaPlugin, Player player, String string) {
        XSound.matchXSound(string).ifPresent ( xSound -> xSound.play ( player.getLocation (), 1f, 1f ) );
    }
}
