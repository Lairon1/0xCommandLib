package com.lairon.libs.xcommandlib.exception;

import com.lairon.libs.xcommandlib.model.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class OnlyPlayerException extends Exception {

    private final CommandSender sender;
    private final SubCommand subCommand;

    /**
     * Thrown if a command can only be used by a player, but not by a player.
     * @param sender The player that sent the command.
     * @param subCommand The subcommand someone is trying to use/
     */
    public OnlyPlayerException(CommandSender sender, SubCommand subCommand) {
        super("The " + subCommand.getSettings().getId() + " command can only be executed by the player");
        Objects.requireNonNull(sender, "sender can not be null");
        Objects.requireNonNull(subCommand, "subCommand can not be null");
        this.sender = sender;
        this.subCommand = subCommand;
    }

    /**
     * @return The player that sent the command.
     */
    public CommandSender getSender() {
        return sender;
    }

    /**
     * @return The subcommand someone is trying to use/
     */
    public SubCommand getSubCommand() {
        return subCommand;
    }
}
