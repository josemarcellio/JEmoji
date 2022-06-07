package com.josemarcellio.jemoji.core.command.subcommand;

import com.josemarcellio.jemoji.common.api.SubCommand;
import com.josemarcellio.jemoji.core.inventory.InventoryMenu;
import com.josemarcellio.jemoji.core.util.Utility;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuiCommand extends SubCommand {


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
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                InventoryMenu.open ( player );
            } else {
                commandSender.sendMessage( Utility.getColor("&7Only player can use this command!"));
            }
        }
    }
}
