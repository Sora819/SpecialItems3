package me.sora819.sampleplugin.commands.subcommands;

import me.sora819.sampleplugin.localization.LocalizationHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ExampleSubCommand implements ISubCommand {


    @Override
    public String getName() {
        return "example";
    }

    @Override
    public String getDescription() {
        return "An example subcommand";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public List<String> getCompletions(String[] args) {
        return List.of("Test", "Test2", "Test3");
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage(LocalizationHandler.getMessage("subcommand.test", true) + String.join(" ", args));
    }
}
