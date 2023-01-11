package com.lairon.libs.xcommandlib;

import com.lairon.libs.xcommandlib.exception.DontHavePermissionException;
import com.lairon.libs.xcommandlib.exception.OnlyPlayerException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Setter
public class CommandExecutor implements org.bukkit.command.CommandExecutor, org.bukkit.command.TabCompleter {

    private final CommandRegistry commandRegistry;
    private Command defaultCommand;
    private Consumer<CommandNotFoundAction> commandNotFoundAction;
    private Consumer<CommandAction> onlyPlayerAction;
    private Consumer<CommandAction> senderDontHasPermissionAction;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (defaultCommand != null) defaultCommand.onCommand(sender, cmd, label, args);
            return false;
        } else {
            Command command = commandRegistry.getCommand(args[0]);
            if (command == null) {
                if (commandNotFoundAction != null)
                    commandNotFoundAction.accept(new CommandNotFoundAction(sender, args[0]));
                return false;
            }
            try {
                if (isAvailableCommand(command, sender))
                    command.onCommand(sender, cmd, label, args);
            } catch (DontHavePermissionException e) {
                if(senderDontHasPermissionAction != null)
                    senderDontHasPermissionAction.accept(new CommandAction(sender, command));
            } catch (OnlyPlayerException e) {
                if(onlyPlayerAction != null)
                    onlyPlayerAction.accept(new CommandAction(sender, command));
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String alias, @NotNull String[] args) {
        List<String> tabs = new ArrayList<>();
        if (args.length == 1) {
            for (Command command : commandRegistry.getCommands()) {
                try {
                    if (isAvailableCommand(command, sender))
                        tabs.add(command.getId());
                } catch (Exception e) {}
            }
        } else {
            Command command = commandRegistry.getCommand(args[0]);
            try {
                if(command != null && isAvailableCommand(command, sender)){
                    List<String> strings = command.onTabComplete(sender, cmd, alias, args);
                    if(strings != null)
                        tabs.addAll(strings);
                }
            } catch (Exception e) {}
        }
        return filter(tabs, args);
    }

    public boolean isAvailableCommand(@NotNull Command command, @NotNull CommandSender sender) throws DontHavePermissionException, OnlyPlayerException {
        if (command.getPermission() != null && !sender.hasPermission(command.getPermission()))
            throw new DontHavePermissionException(sender, command.getPermission());
        if (command.isOnlyPlayer() && !(sender instanceof Player))
            throw new OnlyPlayerException(command);
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

}
