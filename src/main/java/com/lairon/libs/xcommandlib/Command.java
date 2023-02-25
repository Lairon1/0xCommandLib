package com.lairon.libs.xcommandlib;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
@RequiredArgsConstructor
public abstract class Command implements CommandExecutor, TabCompleter {


    private final CommandSettings commandSettings;

    public Command(@NotNull String id,
                   @NotNull String permission,
                   @NotNull boolean onlyPlayer) {
        this(CommandSettings
                .builder()
                .id(id)
                .permission(permission)
                .onlyPlayer(onlyPlayer)
                .build());
    }

    public Command(@NotNull String id,
                   @NotNull String permission) {
        this(CommandSettings
                .builder()
                .id(id)
                .permission(permission)
                .build());
    }

    public Command(@NotNull String id,
                   @NotNull boolean onlyPlayer) {
        this(CommandSettings
                .builder()
                .id(id)
                .onlyPlayer(onlyPlayer)
                .build());
    }

    public Command(@NotNull String id) {
        this(CommandSettings
                .builder()
                .id(id)
                .build());
    }

    public Command(@NotNull String id,
                   @NotNull String permission,
                   @NotNull boolean onlyPlayer,
                   @NonNull List<String> alliances) {
        this(CommandSettings
                .builder()
                .id(id)
                .permission(permission)
                .onlyPlayer(onlyPlayer)
                .alliances(alliances)
                .build());
    }

    public Command(@NotNull String id,
                   @NotNull String permission,
                   @NonNull List<String> alliances) {
        this(CommandSettings
                .builder()
                .id(id)
                .permission(permission)
                .alliances(alliances)
                .build());
    }

    public Command(@NotNull String id,
                   @NotNull boolean onlyPlayer,
                   @NonNull List<String> alliances) {
        this(CommandSettings
                .builder()
                .id(id)
                .onlyPlayer(onlyPlayer)
                .alliances(alliances)
                .build());
    }

    public Command(@NotNull String id,
                   @NonNull List<String> alliances) {
        this(CommandSettings
                .builder()
                .id(id)
                .alliances(alliances)
                .build());
    }

    @Override
    public abstract boolean onCommand(@NonNull CommandSender sender,
                                      @NonNull org.bukkit.command.Command command,
                                      @NonNull String label,
                                      @NonNull String[] args);

    @Override
    @Nullable
    public List<String> onTabComplete(@NonNull CommandSender sender,
                                      @NonNull org.bukkit.command.Command command,
                                      @NonNull String alias,
                                      @NonNull String[] args) {
        return null;
    }

    public String getId() {
        return commandSettings.getId();
    }

    public String getPermission(){
        return commandSettings.getPermission();
    }

    public boolean isOnlyPlayer(){
        return commandSettings.isOnlyPlayer();
    }

    public List<String> getAlliances(){
        return commandSettings.getAlliances();
    }

}
