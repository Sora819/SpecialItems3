package me.sora819.sampleplugin.commands;

import me.sora819.sampleplugin.config.ConfigHandler;
import me.sora819.sampleplugin.localization.LocalizationHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ExampleReloadCommand implements ICommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(LocalizationHandler.getMessage("error.invalid_argument_count", true));
            return true;
        }

        ConfigHandler.reloadConfigurations();
        sender.sendMessage(LocalizationHandler.getMessage("success.reload", true));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return List.of();
    }
}
