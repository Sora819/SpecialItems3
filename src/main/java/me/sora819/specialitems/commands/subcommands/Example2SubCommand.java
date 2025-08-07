package me.sora819.specialitems.commands.subcommands;

import me.sora819.specialitems.SpecialItems;
import me.sora819.specialitems.localization.LocalizationHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Example2SubCommand implements ISubCommand {


    @Override
    public String getName() {
        return "example2";
    }

    @Override
    public String getDescription() {
        return "A second example subcommand";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public List<String> getCompletions(String[] args) {
        if (args.length == 1) {
            return SpecialItems.getInstance().getServer().getOnlinePlayers().stream().map(Player::getName).toList();
        } else if (args.length == 2) {
            return List.of("Test", "Test2", "Test3");
        } else {
            return List.of();
        }
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(LocalizationHandler.getMessage("error.invalid_argument_count", true));
            return;
        }

        Player player = SpecialItems.getInstance().getServer().getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(LocalizationHandler.getMessage("error.player_not_found", true));
            return;
        }

        sender.sendMessage(LocalizationHandler.getMessage("subcommand.test", true) + String.join(" ", args));
        player.sendMessage(LocalizationHandler.getMessage("subcommand.test2", true) + String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
    }
}
