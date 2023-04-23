package com.lairon.libs.xcommandlib;

import com.lairon.libs.xcommandlib.model.SubCommand;

import java.util.*;

public class CommandRegistry {

    private final Map<Class<? extends SubCommand>, SubCommand> commands = new HashMap<>();
    private final Map<String, SubCommand> commandByID = new HashMap<>();

    public void registerCommand(SubCommand subCommand) {
        Objects.requireNonNull(subCommand, "subCommand can not be null");
        if (getCommand(subCommand.getSettings().getId()) != null)
            throw new IllegalArgumentException("Command " + subCommand.getSettings().getId() + " is already registered");
        commands.put(subCommand.getClass(), subCommand);
        commandByID.put(subCommand.getSettings().getId(), subCommand);
        for (String alliance : subCommand.getSettings().getAliases()) {
            commandByID.put(alliance, subCommand);
        }
    }

    public void unregisterCommand(SubCommand subCommand) {
        Objects.requireNonNull(subCommand, "subCommand can not be null");
        commands.remove(subCommand.getClass());
        List<String> alliances = new ArrayList<>();
        for (Map.Entry<String, SubCommand> commandEntry : commandByID.entrySet()) {
            if(commandEntry.getValue() == subCommand) alliances.add(commandEntry.getKey());
        }
        alliances.forEach(commandByID::remove);
    }




    public SubCommand getCommand(String id) {
        Objects.requireNonNull(id, "id can not be null");
        return commandByID
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(id))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    public SubCommand getCommand(Class<? extends SubCommand> commandClass) {
        Objects.requireNonNull(commandClass, "commandClass can not be null");
        return commands.get(commandClass);
    }


    public List<SubCommand> getCommands() {
        return new ArrayList<>(commands.values());
    }


}
