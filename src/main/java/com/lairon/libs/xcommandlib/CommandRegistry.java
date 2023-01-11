package com.lairon.libs.xcommandlib;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandRegistry {

    private Map<Class<? extends Command>, Command> commands = new HashMap<>();

    public void registerCommand(@NotNull Command command){
        if(getCommand(command) != null)
            throw new IllegalArgumentException("Command " + command.getId() + " is already registered");
        commands.put(command.getClass(), command);
    }

    public void unregisterCommand(@NotNull Command command){
        commands.remove(command.getClass());
    }

    public Command getCommand(Command command){
        return getCommand(command.getId());
    }

    @Nullable
    public Command getCommand(@NotNull String id){
        return commands
                .values()
                .stream()
                .filter(command -> command.getId().equalsIgnoreCase(id))
                .findAny()
                .orElse(null);
    }

    @Nullable
    public Command getCommand(@NotNull Class<? extends Command> aClass){
        return commands.get(aClass);
    }


    @NotNull
    public List<Command> getCommands(){
        return new ArrayList<>(commands.values());
    }


}
