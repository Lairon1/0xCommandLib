package com.lairon.libs.xcommandlib;

import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
public abstract class Command implements CommandExecutor, TabCompleter {


    private final String id;
    private String permission;
    private final boolean onlyPlayer;

    public Command(String id, String permission, boolean onlyPlayer) {
        this.id = id;
        this.permission = permission;
        this.onlyPlayer = onlyPlayer;
    }

    public Command(String id, String permission) {
        this.id = id;
        this.permission = permission;
        onlyPlayer = false;
    }

    public Command(String id, boolean onlyPlayer) {
        this.id = id;
        this.onlyPlayer = onlyPlayer;
    }

    public Command(String id) {
        this.id = id;
        onlyPlayer = false;
    }


    @Override
    public abstract boolean onCommand(@NotNull CommandSender sender,
                             org.bukkit.command.@NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args);

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
