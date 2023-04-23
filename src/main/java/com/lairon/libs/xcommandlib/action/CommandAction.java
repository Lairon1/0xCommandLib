package com.lairon.libs.xcommandlib.action;

import com.lairon.libs.xcommandlib.model.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.Objects;


public class CommandAction {

    private final CommandSender commandSender;
    private final SubCommand subCommand;

    /**
     *  If any of the Actions was called, then the command was not executed for some reason.
     * @param commandSender The entity that sent the command. Can be console, player or even command block.
     * @param subCommand The subcommand the player has called.
     */
    public CommandAction(CommandSender commandSender, SubCommand subCommand) {
        Objects.requireNonNull(commandSender, "commandSender can not be null");
        Objects.requireNonNull(subCommand, "subCommand can not be null");
        this.commandSender = commandSender;
        this.subCommand = subCommand;
    }

    /**
     * @return The entity that sent the command. Can be console, player or even command block.
     */
    public CommandSender getCommandSender() {
        return commandSender;
    }

    /**
     * @return The subcommand the player has called.
     */
    public SubCommand getCommand() {
        return subCommand;
    }
}
