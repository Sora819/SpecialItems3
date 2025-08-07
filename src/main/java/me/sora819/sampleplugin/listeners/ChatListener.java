package me.sora819.sampleplugin.listeners;

import me.sora819.sampleplugin.SamplePlugin;
import me.sora819.sampleplugin.localization.LocalizationHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        SamplePlugin.getInstance().getLogger().info(e.getPlayer().getName() + LocalizationHandler.getMessage("chat.sent"));
    }
}
