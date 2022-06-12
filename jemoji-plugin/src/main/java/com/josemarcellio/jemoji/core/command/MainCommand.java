package com.josemarcellio.jemoji.core.command;

import com.josemarcellio.jemoji.common.api.SubCommand;
import com.josemarcellio.jemoji.core.JEmoji;
import com.josemarcellio.jemoji.core.command.subcommand.GuiCommand;
import com.josemarcellio.jemoji.core.command.subcommand.ReloadCommand;
import com.josemarcellio.jemoji.core.util.Utility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import java.util.ArrayList;

public class MainCommand implements CommandExecutor {

    public final JEmoji plugin;

    public String getMainCommand() {
        return "jemoji";
    }

    private final ArrayList<SubCommand> subCommand = new ArrayList<> ();

    public MainCommand(JEmoji plugin) {
        this.plugin = plugin;
        subCommand.add(new GuiCommand (plugin));
        subCommand.add(new ReloadCommand (plugin));
    }

    private ArrayList<SubCommand> getSubCommand() {
        return subCommand;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            for (int i = 0; i < getSubCommand ().size (); i++) {
                if (args[0].equalsIgnoreCase ( getSubCommand ().get ( i ).getCommandName ())) {
                    if (sender.hasPermission(getSubCommand().get(i).getPermission())) {
                        getSubCommand ().get ( i ).execute ( sender, args );
                    } else {
                        sender.sendMessage(Utility.getColor( "&cYou don't have permission " + getSubCommand().get(i).getPermission()));
                    }
                }
            }
        } else {
            sender.sendMessage ( Utility.getColor ( "&a&lJEmoji &6&l☕" ) );
            sender.sendMessage( "" );
            sender.sendMessage( Utility.getColor("&6List Command:" ) );
            for (int i = 0; i < getSubCommand ().size (); i++) {
                sender.sendMessage ( Utility.getColor(" &9• &7/" + getMainCommand() + " " + getSubCommand().get(i).getCommandName() + " &9- &7"
                        + getSubCommand ().get(i).getDescription() ));
            }
        }
        return false;
    }
}
