package com.lairon.libs.xcommandlib;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.command.CommandSender;

@Data
@AllArgsConstructor
public class CommandAction {

    private final CommandSender commandSender;
    private final Command command;

}
