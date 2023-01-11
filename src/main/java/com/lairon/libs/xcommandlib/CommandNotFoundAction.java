package com.lairon.libs.xcommandlib;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.command.CommandSender;

@Data
@AllArgsConstructor
public class CommandNotFoundAction {

    private CommandSender commandSender;
    private String commandID;

}
