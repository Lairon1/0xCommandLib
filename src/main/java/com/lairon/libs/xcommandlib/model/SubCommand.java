package com.lairon.libs.xcommandlib.model;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;
import java.util.Objects;

public abstract class SubCommand implements CommandExecutor, TabCompleter {


    private final SubCommandSettings settings;

    /**
     * @param settings Subcommand settings that help configure subcommands
     */
    public SubCommand(SubCommandSettings settings) {
        Objects.requireNonNull(settings, "settings can not be null");

        this.settings = settings;
    }

    @Override
    public abstract boolean onCommand(CommandSender sender,
                                      org.bukkit.command.Command command,
                                      String label,
                                      String[] args);

    @Override
    public List<String> onTabComplete(CommandSender sender,
                                      org.bukkit.command.Command command,
                                      String alias,
                                      String[] args) {
        return null;
    }

    /**
     * @return Subcommand settings that help configure subcommands
     */
    public SubCommandSettings getSettings() {
        return settings;
    }
}
