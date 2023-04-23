package com.lairon.libs.xcommandlib.exception;

import com.lairon.libs.xcommandlib.model.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class DontHavePermissionException extends Exception {

    private final CommandSender sender;
    private final SubCommand subCommand;

    /**
     * This error is thrown when a player tries to use a command but does not have permission to use it.
     * @param sender The player that sent the command.
     * @param subCommand The subcommand someone is trying to use
     */
    public DontHavePermissionException(CommandSender sender, SubCommand subCommand) {
        super("Sender " + sender.getName() + " dont have permission " + subCommand.getSettings().getPermission());
        Objects.requireNonNull(sender, "sender can not be null");
        Objects.requireNonNull(subCommand, "subCommand can not be null");
        this.sender = sender;
        this.subCommand = subCommand;
    }

    /**
     * @return The subcommand someone is trying to use
     */
    public CommandSender getSender() {
        return sender;
    }

    /**
     * @return The subcommand someone is trying to use
     */
    public SubCommand getSubCommand() {
        return subCommand;
    }
}
