package me.sora819.specialitems.commands.subcommands;

import me.sora819.specialitems.SpecialItems;
import me.sora819.specialitems.localization.LocalizationHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class LanguageSubCommand implements ISubCommand {


    @Override
    public String getName() {
        return "language";
    }

    @Override
    public String getDescription() {
        return "Set the plugin language";
    }

    @Override
    public String getPermission() {
        return "specialitems.language";
    }

    @Override
    public List<String> getCompletions(String[] args) {
        return switch (args.length) {
            case 1 -> LocalizationHandler.getLocales();
            default -> List.of();
        };
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(LocalizationHandler.getMessage("error.invalid_argument_count", true));
            return;
        }

        if (!LocalizationHandler.getLocales().contains(args[1])) {
            sender.sendMessage(LocalizationHandler.getMessage("error.invalid_language", true));
            return;
        }

        LocalizationHandler.setLanguage(args[1]);
        sender.sendMessage(LocalizationHandler.getMessage("success.language_set", true));
    }
}
