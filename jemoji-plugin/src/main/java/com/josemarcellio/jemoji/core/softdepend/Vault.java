package com.josemarcellio.jemoji.core.softdepend;

import com.josemarcellio.jemoji.common.economy.Economy;
import org.bukkit.entity.Player;

public class Vault implements Economy {

    private static net.milkbowl.vault.economy.Economy economy;

    @Override
    public double getMoney(Player player) {
        return economy.getBalance ( player );
    }

    @Override
    public void takeMoney(Player player, double money) {
        economy.withdrawPlayer ( player, money );
    }

    public static void registerEconomy(net.milkbowl.vault.economy.Economy economy) {
        Vault.economy = economy;
    }
}
