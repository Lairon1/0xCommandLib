## Give me what I need
### dependency

```xml
    <repository>
        <id>github</id>
        <name>GitHub Packages</name>
        <url>https://maven.pkg.github.com/Lairon1/0xCommandLib</url>
    </repository>
```

```xml
        <dependency>
            <groupId>com.lairon.libs</groupId>
            <artifactId>0xCommandLib</artifactId>
            <version>2.0</version>
            <scope>compile</scope>
        </dependency>
```

## About
XCommandLib is a simple library that will help you quickly and easily create commands and subcommands. You can also configure your subcommands as you go.
## How to use?

### How to create and register my command?
```java
    public void onEnable(){
        // To get started, create a CommandRegistry,
        // you need it in order to store your subcommands
        // in it and so that you can easily manage them.
        CommandRegistry commandRegistry = new CommandRegistry();

        // Create a CommandExecutor,
        // you need it in order to register it as a Bukkit command.
        // It will manage all subcommands for you.
        CommandExecutor commandExecutor = new CommandExecutor(commandRegistry);

        // Register the CommandExecutor as a regular command for Bukkit
        // You can also register the CommandExecutor as a TabCompleter,
        // then my library itself will sort, filter and display help in the command water
        PluginCommand yourBukkitCommand = Bukkit.getPluginCommand("YourCommand");
        yourBukkitCommand.setExecutor(commandExecutor);
        yourBukkitCommand.setTabCompleter(commandExecutor);

        // Now you can add your sub-commands to the CommandRegistry and you can use them in the game!
        // Game Usage: /YourCommand <SubCommandID> <params>...
        commandRegistry.registerCommand(new YourSubCommand());
        
        // This is how you can set a standard command handler,
        // it will be called when the player has not entered any subcommands.
        // You can use it as a command to display help to the player.
        // Just register HelpCommand as a regular help subcommand and as a DefaultCommand
        commandExecutor.setDefaultCommand(new HelpCommand());
        }
```

### How can I create my own sub command?
```java
// First, inherit the SubCommand class and implement its
// onCommand method and class constructor
public class YourSubCommand extends SubCommand {

    // In the super class constructor, pass SubCommandSettings.
    // You can use builder to easily customize your sub-command.
    // If you only need to specify an ID then you can use new SubCommandSettings(ID);
    public YourSubCommand() {
        super(SubCommandSettings
                // The ID of your subcommand,
                // so that the library understands
                // which subcommand it needs to use.
                .builder("YourSubCommand") // (NECESSARILY)
                // If you want only players to use
                // your sub-command, set it to true
                .onlyPlayer(true) // (NOT NECESSARY)
                // You can set the permissions that the
                // subcommand will require in order to execute it.
                .permission("some.permission") // (NOT NECESSARY)
                // You can set aliases for your subcommand
                .aliases(List.of("alias1", "alias2")) // (NOT NECESSARY)
                // Or add aliases 1 at a time
                .alias("alias3") // (NOT NECESSARY)
                .alias("alias4") // (NOT NECESSARY)
                .build());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Here you can add logic to your subcommand.
        // The implementation may be the same as in vanilla Bukkit.
        // The library itself will make all the checks for onlyPlayer and permission.
        // Note that in the args array,
        // the first element will always be
        // the id of your subcommand or its alias.
        return false;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // Here you can issue your Tab Complete for sub-commands,
        // the library itself will filter and sort everything for you
        return null;
    }
}
```

### Actions
If you want to make your own custom messages that the player has insufficient rights or that the subcommand is not found, then use the Actions system.
```java
    public void onEnable() {
        CommandRegistry commandRegistry = new CommandRegistry();
        CommandExecutor commandExecutor = new CommandExecutor(commandRegistry);

        // This action is used to track the event
        // when the player entered an unknown subcommand
        commandExecutor.setCommandNotFoundAction(action -> {
            action.getCommandID(); // ID for the command entered by the player
            action.getCommandSender(); // The player itself, or the ConsoleSender object
            // Here you can send a message to the sender
            // of the command that the command was not found.
            action.getCommandSender().sendMessage("Command " + action.getCommandID() + " not found.");
        });

        // This Action will be called when the sender
        // does not have enough rights to use this command
        commandExecutor.setSenderDontHasPermissionAction(action -> {
            action.getCommand(); // Subcommands that someone tried to call
            action.getCommandSender(); // The sender of the command
            // Here you can also send some message to
            // the sender of the command that he does not have permissions
            action.getCommandSender().sendMessage("You must have %s permission to use the %s command"
                    .formatted(
                            action.getCommand().getSettings().getPermission(),
                            action.getCommand().getSettings().getId()
                    ));
        });

        // This Action is triggered when the command
        // was used by the console but it is only for the player.
        commandExecutor.setOnlyPlayerAction(action -> {
            action.getCommand(); // Subcommands that someone tried to call
            action.getCommandSender(); // The sender of the command
        });
    }
```