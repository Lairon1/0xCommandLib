package com.lairon.libs.xcommandlib.action;

import org.bukkit.command.CommandSender;

import java.util.Objects;


public class CommandNotFoundAction {

    private final CommandSender commandSender;
    private final String commandID;

    /**
     * If any of the Actions was called, then the command was not executed for some reason.
     * @param commandSender The entity that sent the command. Can be console, player or even command block.
     * @param commandID The id of the command the player is trying to call.
     */
    public CommandNotFoundAction(CommandSender commandSender, String commandID) {
        Objects.requireNonNull(commandSender, "commandSender can not be null");
        Objects.requireNonNull(commandID, "commandID can not be null");
        this.commandSender = commandSender;
        this.commandID = commandID;
    }

    /**
     * @return The entity that sent the command. Can be console, player or even command block.
     */
    public CommandSender getCommandSender() {
        return commandSender;
    }

    /**
     * @return The id of the command the player is trying to call.
     */
    public String getCommandID() {
        return commandID;
    }
}
