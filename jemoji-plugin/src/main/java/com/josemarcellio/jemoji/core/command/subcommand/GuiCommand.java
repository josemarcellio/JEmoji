package com.josemarcellio.jemoji.core.command.subcommand;

import com.josemarcellio.jemoji.common.api.SubCommand;
import com.josemarcellio.jemoji.core.inventory.InventoryMenu;
import com.josemarcellio.jemoji.core.util.Utility;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiCommand extends SubCommand {

    private final JavaPlugin plugin;

    public GuiCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

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
        return "Open Emoji GUI";
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (args.length < 2) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                new InventoryMenu(plugin).open ( player );
            } else {
                commandSender.sendMessage( Utility.getColor("&cOnly player can use this command!"));
            }
        }
    }
}
