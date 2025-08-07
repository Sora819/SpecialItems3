package me.sora819.specialitems.commands.subcommands;

import me.sora819.specialitems.SpecialItems;
import me.sora819.specialitems.items.ICustomItem;
import me.sora819.specialitems.items.ItemRegistry;
import me.sora819.specialitems.localization.LocalizationHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetSubCommand implements ISubCommand {


    @Override
    public String getName() {
        return "get";
    }

    @Override
    public String getDescription() {
        return "Give yourself a special item";
    }

    @Override
    public String getPermission() {
        return "specialitems.get";
    }

    @Override
    public List<String> getCompletions(String[] args) {
        return switch (args.length) {
            case 1 -> new ArrayList<>(ItemRegistry.getItemIDs());
            default -> List.of();
        };
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(LocalizationHandler.getMessage("error.invalid_argument_count", true));
            return;
        }

        Player player = (Player)sender;
        ICustomItem customItem = ItemRegistry.getItem(args[1]);

        player.getInventory().addItem(customItem.getItemStack());

        sender.sendMessage(LocalizationHandler.getMessage("command.item_given", true) + customItem.getName());
    }
}
