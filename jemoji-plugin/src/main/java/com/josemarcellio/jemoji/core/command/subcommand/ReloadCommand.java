package com.josemarcellio.jemoji.core.command.subcommand;

import com.josemarcellio.jemoji.common.api.SubCommand;
import com.josemarcellio.jemoji.core.JEmoji;
import com.josemarcellio.jemoji.core.util.Utility;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {

    @Override
    public String getCommandName() {
        return "reload";
    }

    @Override
    public String getPermission() {
        return "jemoji.reload";
    }

    @Override
    public String getDescription() {
        return "Reload Configuration";
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (args.length < 2) {
            JEmoji.instance.reloadConfig();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getOpenInventory ().getTitle().equals( Utility.getColor(JEmoji.instance.getConfig().getString("gui-name")))) {
                    player.closeInventory ();
                }
            }
            commandSender.sendMessage(Utility.getColor("&7JEmoji config reloaded!"));
        }
    }
}
