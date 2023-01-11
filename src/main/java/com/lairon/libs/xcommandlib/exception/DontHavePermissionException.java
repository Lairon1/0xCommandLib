package com.lairon.libs.xcommandlib.exception;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DontHavePermissionException extends Exception {
    public DontHavePermissionException(@NotNull CommandSender sender, @NotNull String permission) {
        super("Sender " + sender.getName() + " dont have permission " + permission);
    }
}
