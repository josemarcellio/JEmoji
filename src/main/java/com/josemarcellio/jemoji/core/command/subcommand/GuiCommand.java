package com.josemarcellio.jemoji.core.command.subcommand;

import com.josemarcellio.jemoji.common.PluginInstance;
import com.josemarcellio.jemoji.common.api.SubCommand;
import com.josemarcellio.jemoji.core.inventory.InventoryMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuiCommand extends SubCommand implements PluginInstance {


    @Override
    public String getCommandName() {
        return "gui";
    }

    @Override
    public String getPermission() {
        return "jemoji.gui";
    }

    @Override
    public String getDescription() {
        return "Open GUI";
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (args.length < 2) {
            Player player = (Player)commandSender;
            InventoryMenu.open(player);
        }
    }
}
