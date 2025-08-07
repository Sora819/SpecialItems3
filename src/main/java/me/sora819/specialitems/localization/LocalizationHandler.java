package me.sora819.specialitems.localization;

import me.sora819.specialitems.config.ConfigAdapter;
import me.sora819.specialitems.config.ConfigHandler;
import me.sora819.specialitems.config.LocalizationConfig;
import org.bukkit.ChatColor;

public class LocalizationHandler {
    public static ConfigAdapter defaultMessagesConfig = ConfigHandler.defaultMessagesConfig;
    public static ConfigAdapter messagesConfig = ConfigHandler.messagesConfig;

    public static String getRawMessage(String key) {
        return messagesConfig.get(key) != null ?
                messagesConfig.get(key) :
                defaultMessagesConfig.get(key);
    }

    public static String getRawMessage(String key, Boolean includePrefix) {
        String message = messagesConfig.get(key) != null ? messagesConfig.get(key) : defaultMessagesConfig.get(key);
        String prefix = ConfigHandler.defaultConfig.get("prefix");

        return includePrefix ? prefix + message : message;
    }

    public static String getMessage(String key) {
        return messagesConfig.get(key) != null ?
                ChatColor.translateAlternateColorCodes('&', messagesConfig.get(key)) :
                ChatColor.translateAlternateColorCodes('&', defaultMessagesConfig.get(key));
    }

    public static String getMessage(String key, Boolean includePrefix) {
        String message = messagesConfig.get(key) != null ? messagesConfig.get(key) : defaultMessagesConfig.get(key);
        String prefix = ConfigHandler.defaultConfig.get("prefix");

        return includePrefix ? ChatColor.translateAlternateColorCodes('&', prefix + message ) : ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void setLanguage(String language) {
        ConfigHandler.defaultConfig.set("language", language);
        messagesConfig = new LocalizationConfig(language);
    }
}
