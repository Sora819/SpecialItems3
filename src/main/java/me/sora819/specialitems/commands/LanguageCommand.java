package me.sora819.specialitems.commands;

import me.sora819.specialitems.localization.LocalizationHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class LanguageCommand implements ICommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(LocalizationHandler.getMessage("error.invalid_argument_count", true));
            return true;
        }

        LocalizationHandler.setLanguage(args[0]);
        sender.sendMessage(LocalizationHandler.getMessage("success.language_set", true));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>(List.of("en", "hu"));

        completions.removeIf(option -> !option.toLowerCase().startsWith(args[0].toLowerCase()));
        return completions;
    }
}
