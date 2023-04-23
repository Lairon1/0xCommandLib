package com.lairon.libs.xcommandlib.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Subcommand settings that help configure subcommands
 */
public class SubCommandSettings {

    private final String id;
    private boolean onlyPlayer;
    private List<String> aliases;
    private String permission;

    /**
     * @param id Command ID. Like this /somecommand subCommandID <--
     * @param onlyPlayer If true then the command can only be fired on behalf of the player.
     *                   If false then the command can be executed by anyone.
     * @param aliases Command alliances, used to add command short id's.
     * @param permission The permissions required to use the command.
     *                   If null then permissions are not needed.
     */
    public SubCommandSettings(String id, boolean onlyPlayer, List<String> aliases, String permission) {
        Objects.requireNonNull(id, "id can not be null");
        this.id = id;
        this.onlyPlayer = onlyPlayer;
        this.aliases = aliases;
        this.permission = permission;
    }

    /**
     * @param id Command ID. Like this /somecommand subCommandID <--
     */
    public SubCommandSettings(String id) {
        this(id, false, null, null);
    }

    /**
     * @return Command ID. Like this /somecommand subCommandID <--
     */
    public String getId() {
        return id;
    }

    /**
     * @return If true then the command can only be fired on behalf of the player.
     * If false then the command can be executed by anyone.
     */
    public boolean isOnlyPlayer() {
        return onlyPlayer;
    }

    /**
     * @param onlyPlayer If true then the command can only be fired on behalf of the player.
     *                  If false then the command can be executed by anyone.
     */
    public void setOnlyPlayer(boolean onlyPlayer) {
        this.onlyPlayer = onlyPlayer;
    }

    /**
     * @return Command alliances, used to add command short id's.
     */
    public List<String> getAliases() {
        return aliases;
    }

    /**
     * @param aliases Command alliances, used to add command short id's.
     */
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    /**
     * @return The permissions required to use the command. If null then permissions are not needed.
     */
    public String getPermission() {
        return permission;
    }

    /**
     * @param permission The permissions required to use the command. If null then permissions are not needed.
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    public static Builder builder(String id){
        return new Builder(id);
    }

    public static class Builder{

        private final String id;
        private boolean onlyPlayer = false;
        private List<String> aliases;
        private String permission;

        public Builder(String id) {
            Objects.requireNonNull(id, "id can not be null");
            this.id = id;
        }

        public Builder onlyPlayer(boolean onlyPlayer){
            this.onlyPlayer = onlyPlayer;
            return this;
        }
        public Builder aliases(List<String> alliances){
            this.aliases = alliances;
            return this;
        }

        public Builder alias(String alias){
            Objects.requireNonNull(alias, "alias can not be null");
            if(alias.isEmpty()) throw new IllegalArgumentException("alias can not be empty");
            if(aliases == null) aliases = new ArrayList<>();
            aliases.add(alias);
            return this;
        }

        public Builder permission(String permission){
            this.permission = permission;
            return this;
        }

        public SubCommandSettings build(){
            return new SubCommandSettings(id, onlyPlayer, aliases, permission);
        }

    }

}
