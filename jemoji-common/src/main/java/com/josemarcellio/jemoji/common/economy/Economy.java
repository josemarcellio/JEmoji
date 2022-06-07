package com.josemarcellio.jemoji.common.economy;

import org.bukkit.entity.Player;

public interface Economy {

    double getMoney(Player player);

    void takeMoney(Player player, double money);
}