package me.sora819.specialitems.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface ISubCommand {
    String getName();
    String getDescription();
    String getPermission();
    List<String> getCompletions(String[] args);
    void execute(CommandSender sender, Command cmd, String label, String[] args);
}
