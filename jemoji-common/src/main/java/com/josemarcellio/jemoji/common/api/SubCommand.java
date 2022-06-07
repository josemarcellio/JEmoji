package com.josemarcellio.jemoji.common.api;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    public abstract String getCommandName();

    public abstract String getPermission();

    public abstract String getDescription();

    public abstract void execute(CommandSender commandSender, String[] args);
}
