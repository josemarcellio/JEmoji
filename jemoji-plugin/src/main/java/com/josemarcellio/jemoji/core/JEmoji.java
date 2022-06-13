package com.josemarcellio.jemoji.core;

import com.josemarcellio.jemoji.common.economy.Economy;
import com.josemarcellio.jemoji.core.command.MainCommand;
import com.josemarcellio.jemoji.core.listener.InventoryClickListener;
import com.josemarcellio.jemoji.core.listener.PlayerChatListener;
import com.josemarcellio.jemoji.core.listener.SignChangeListener;
import com.josemarcellio.jemoji.core.metrics.Metrics;
import com.josemarcellio.jemoji.core.softdepend.Vault;
import com.josemarcellio.jemoji.core.util.Utility;
import com.josemarcellio.updatechecker.UpdateChecker;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class JEmoji extends JavaPlugin {

    private final MainCommand mainCommand = new MainCommand(this);
    private static Economy economy;

    public void register(Listener... listeners) {
        Arrays.stream ( listeners ).forEach ( listener -> this.getServer ().getPluginManager ().registerEvents ( listener, this ) );
    }

    @Override
    public void onEnable() {

        getLogger().info("JEmoji by JoseMarcellio");

        new Metrics (this, 15391);

        saveDefaultConfig ();

        new UpdateChecker ().setJavaPlugin(this)
                .setResourceId(102447)
                .setMessage( Utility.getColor("&c=============================================================" +
                        "\n\n&cYour JEmoji is outdated, please update your JEmoji!" +
                        "\n\n&cThere is a new version {latest_version}, you are still using version {current_version}" +
                        "\n\n&c============================================================="))
                .init();

        if (this.getServer().getPluginManager().isPluginEnabled("Vault")) {
            RegisteredServiceProvider<?> rsp = this.getServer ().getServicesManager ().getRegistration ( net.milkbowl.vault.economy.Economy.class );
            Vault.registerEconomy ( (net.milkbowl.vault.economy.Economy) rsp.getProvider () );
            economy = new Vault ();
        }

        register(new PlayerChatListener (this), new InventoryClickListener (this),
                new SignChangeListener (this));

        getCommand(mainCommand.getMainCommand()).setExecutor(mainCommand);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling JEmoji");
    }

    public static Economy getEconomy() {
        return economy;
    }
}
