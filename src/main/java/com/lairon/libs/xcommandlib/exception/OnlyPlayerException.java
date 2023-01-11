package com.lairon.libs.xcommandlib.exception;

import org.jetbrains.annotations.NotNull;
import com.lairon.libs.xcommandlib.Command;

public class OnlyPlayerException extends Exception {

    public OnlyPlayerException(@NotNull Command command) {
        super("The " + command.getId() + " command can only be executed by the player");
    }

}
