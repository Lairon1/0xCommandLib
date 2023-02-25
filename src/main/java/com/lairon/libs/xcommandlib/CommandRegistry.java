package com.lairon.libs.xcommandlib;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandRegistry {

    private Map<Class<? extends Command>, Command> commands = new HashMap<>();
    private Map<String, Command> commandByID = new HashMap<>();

    public void registerCommand(@NotNull Command command) {
        if (getCommand(command) != null)
            throw new IllegalArgumentException("Command " + command.getId() + " is already registered");
        commands.put(command.getClass(), command);
        commandByID.put(command.getId(), command);
        for (String alliance : command.getAlliances()) {
            commandByID.put(alliance, command);
        }
    }

    public void unregisterCommand(@NotNull Command command) {
        commands.remove(command.getClass());
        List<String> alliances = new ArrayList<>();
        for (Map.Entry<String, Command> commandEntry : commandByID.entrySet()) {
            if(commandEntry.getValue() == command) alliances.add(commandEntry.getKey());
        }
        alliances.forEach(commandByID::remove);
    }

    public Command getCommand(@NonNull Command command) {
        return getCommand(command.getId());
    }

    @Nullable
    public Command getCommand(@NotNull String id) {
        return commandByID
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(id))
                .map(entry -> entry.getValue())
                .findFirst()
                .orElse(null);
    }

    @Nullable
    public Command getCommand(@NotNull Class<? extends Command> aClass) {
        return commands.get(aClass);
    }


    @NotNull
    public List<Command> getCommands() {
        return new ArrayList<>(commands.values());
    }


}
