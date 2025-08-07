package me.sora819.specialitems.commands.subcommands;

import me.sora819.specialitems.localization.LocalizationHandler;
import me.sora819.specialitems.utils.NBTManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DebugSubCommand implements ISubCommand {


    @Override
    public String getName() {
        return "debug";
    }

    @Override
    public String getDescription() {
        return "Show dev informations about the currently held item";
    }

    @Override
    public String getPermission() {
        return "specialitems.debug";
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

        Player player = (Player)sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        String custom_id = NBTManager.getNBT(item, "custom_item");

        sender.sendMessage(custom_id != null ? custom_id : "This is not a custom item");

        if (custom_id != null) {
            String nbt = NBTManager.getNBT(item);

            sender.sendMessage(nbt != null ? nbt : "There is no nbt data on this item");
        }
    }
}
