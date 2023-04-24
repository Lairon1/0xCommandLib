package com.lairon.libs.xcommandlib;

import com.lairon.libs.xcommandlib.action.CommandAction;
import com.lairon.libs.xcommandlib.action.CommandNotFoundAction;
import com.lairon.libs.xcommandlib.exception.DontHavePermissionException;
import com.lairon.libs.xcommandlib.exception.OnlyPlayerException;
import com.lairon.libs.xcommandlib.model.SubCommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;


public class CommandExecutor implements org.bukkit.command.CommandExecutor, org.bukkit.command.TabCompleter {

    private final CommandRegistry commandRegistry;
    private SubCommand defaultCommand;
    private Consumer<CommandNotFoundAction> commandNotFoundAction;
    private Consumer<CommandAction> onlyPlayerAction;
    private Consumer<CommandAction> senderDontHasPermissionAction;

    public CommandExecutor(CommandRegistry commandRegistry) {
        Objects.requireNonNull(commandRegistry, "commandRegistry can not be null");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public boolean onCommand(CommandSender sender,
                             org.bukkit.command.Command cmd,
                             String label,
                             String[] args) {
        Objects.requireNonNull(sender, "sender can not be null");
        Objects.requireNonNull(cmd, "cmd can not be null");
        Objects.requireNonNull(label, "label can not be null");
        Objects.requireNonNull(args, "args can not be null");
        if (args.length == 0) {
            if (defaultCommand != null) defaultCommand.onCommand(sender, cmd, label, args);
            return false;
        } else {
            SubCommand subCommand = commandRegistry.getCommand(args[0]);
            if (subCommand == null) {
                if (commandNotFoundAction != null)
                    commandNotFoundAction.accept(new CommandNotFoundAction(sender, args[0]));
                return false;
            }
            try {
                if (isAvailableCommand(subCommand, sender))
                    subCommand.onCommand(sender, cmd, label, args);
            } catch (DontHavePermissionException e) {
                if (senderDontHasPermissionAction != null)
                    senderDontHasPermissionAction.accept(new CommandAction(sender, subCommand));
            } catch (OnlyPlayerException e) {
                if(onlyPlayerAction != null)
                    onlyPlayerAction.accept(new CommandAction(sender, subCommand));
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender,
                                      org.bukkit.command.Command cmd,
                                      String alias,
                                      String[] args) {
        Objects.requireNonNull(sender, "sender can not be null");
        Objects.requireNonNull(cmd, "cmd can not be null");
        Objects.requireNonNull(args, "args can not be null");
        List<String> tabs = new ArrayList<>();
        if (args.length == 1) {
            for (SubCommand subCommand : commandRegistry.getCommands()) {
                try {
                    if (isAvailableCommand(subCommand, sender)) {
                        tabs.add(subCommand.getSettings().getId());
                        tabs.addAll(subCommand.getSettings().getAliases());
                    }
                } catch (Exception ignored) {
                }
            }
        } else {
            SubCommand subCommand = commandRegistry.getCommand(args[0]);
            try {
                if (subCommand != null && isAvailableCommand(subCommand, sender)) {
                    List<String> strings = subCommand.onTabComplete(sender, cmd, alias, args);
                    if (strings != null)
                        tabs.addAll(strings);
                }
            } catch (Exception ignored) {
            }
        }
        return filter(tabs, args);
    }

    public boolean isAvailableCommand(SubCommand subCommand,
                                      CommandSender sender) throws DontHavePermissionException, OnlyPlayerException {
        if (subCommand.getSettings().getPermission() != null && !sender.hasPermission(subCommand.getSettings().getPermission()))
            throw new DontHavePermissionException(sender, subCommand);
        if (subCommand.getSettings().isOnlyPlayer() && !(sender instanceof Player))
            throw new OnlyPlayerException(sender, subCommand);
        return true;
    }

    private List<String> filter(List<String> list, String[] args) {
        if (list == null) return null;
        if (list.isEmpty()) return null;
        String last = args[args.length - 1];
        List<String> result = new ArrayList<>();
        for (String arg : list) {
            if (arg.toLowerCase().contains(last.toLowerCase()))
                result.add(arg);
        }
        return result;
    }

    public void setCommandNotFoundAction(Consumer<CommandNotFoundAction> commandNotFoundAction) {
        this.commandNotFoundAction = commandNotFoundAction;
    }

    public void setOnlyPlayerAction(Consumer<CommandAction> onlyPlayerAction) {
        this.onlyPlayerAction = onlyPlayerAction;
    }

    public void setSenderDontHasPermissionAction(Consumer<CommandAction> senderDontHasPermissionAction) {
        this.senderDontHasPermissionAction = senderDontHasPermissionAction;
    }

    public void setDefaultCommand(SubCommand defaultCommand) {
        this.defaultCommand = defaultCommand;
    }
}
