package me.sora819.sampleplugin.commands;

import me.sora819.sampleplugin.SamplePlugin;
import me.sora819.sampleplugin.commands.subcommands.ISubCommand;
import me.sora819.sampleplugin.localization.LocalizationHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.reflections.Reflections;

import java.util.*;

public class ExampleMainCommand implements ICommand {
    private static final Map<String, ISubCommand> subCommands = new HashMap<>();

    static {
        autoRegisterSubCommands();
    }

    private static void autoRegisterSubCommands() {
        String packageName = ExampleMainCommand.class.getPackage().getName() + ".subcommands";
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends ISubCommand>> classes = reflections.getSubTypesOf(ISubCommand.class);

        for (Class<? extends ISubCommand> clazz : classes) {
            try {
                ISubCommand cmd = clazz.getDeclaredConstructor().newInstance();
                subCommands.put(cmd.getName().toLowerCase(), cmd);
                SamplePlugin.getInstance().getLogger().info(LocalizationHandler.getMessage("success.subcommand_load") + cmd.getName());
            } catch (Exception e) {
                SamplePlugin.getInstance().getLogger().warning(LocalizationHandler.getMessage("error.subcommand_load") + clazz.getSimpleName());
                SamplePlugin.getInstance().getLogger().warning(e.toString());
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ISubCommand subCommand = subCommands.get(args[0].toLowerCase());

        if (subCommand == null) {
            sender.sendMessage(LocalizationHandler.getMessage("error.invalid_usage", true));
            return true;
        }

        subCommand.execute(sender, cmd, label, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        // Empty if there are no args
        if (args.length == 0) {
            return List.of();
        }

        // Show possible subcommands
        else if (args.length == 1) {
            List<String> completions = new ArrayList<>(subCommands.keySet());

            completions.removeIf(option -> !option.toLowerCase().startsWith(args[0].toLowerCase()));
            return completions;
        }

        // Delegate tab completion to subcommand completions if a valid subcommand is typed
        else {
            if (!subCommands.containsKey(args[0])) {
                return List.of();
            }

            List<String> completions = new ArrayList<>(subCommands.get(args[0].toLowerCase()).getCompletions(Arrays.copyOfRange(args, 1, args.length)));

            completions.removeIf(option -> !option.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
            return completions;
        }
    }
}
