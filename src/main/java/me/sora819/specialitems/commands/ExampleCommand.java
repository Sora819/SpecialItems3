package me.sora819.specialitems.commands;

import me.sora819.specialitems.SpecialItems;
import me.sora819.specialitems.localization.LocalizationHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ExampleCommand implements ICommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LocalizationHandler.getMessage("error.must_be_player", true));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(LocalizationHandler.getMessage("error.invalid_argument_count", true));
            return true;
        }

        sender.sendMessage(LocalizationHandler.getMessage("success.command_run", true));
        sender.sendMessage(args[0]);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>(SpecialItems.getInstance().getServer().getOnlinePlayers().stream().map(Player::getName).toList());

        completions.removeIf(option -> !option.toLowerCase().startsWith(args[0].toLowerCase()));
        return completions;
    }
}
