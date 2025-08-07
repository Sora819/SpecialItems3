package me.sora819.specialitems.listeners;

import me.sora819.specialitems.SpecialItems;
import me.sora819.specialitems.localization.LocalizationHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        SpecialItems.getInstance().getLogger().info(e.getPlayer().getName() + LocalizationHandler.getMessage("chat.sent"));
    }
}
