package me.sora819.specialitems.commands.subcommands;

import me.sora819.specialitems.config.ConfigHandler;
import me.sora819.specialitems.localization.LocalizationHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadSubCommand implements ISubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload plugin configurations";
    }

    @Override
    public String getPermission() {
        return "specialitems.reload";
    }

    @Override
    public List<String> getCompletions(String[] args) {
        return switch (args.length) {
            default -> List.of();
        };
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(LocalizationHandler.getMessage("error.invalid_argument_count", true));
            return;
        }

        ConfigHandler.reloadConfigurations();
        sender.sendMessage(LocalizationHandler.getMessage("success.reload", true));
    }
}
