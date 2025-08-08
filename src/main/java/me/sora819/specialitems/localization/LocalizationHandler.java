package me.sora819.specialitems.localization;

import me.sora819.specialitems.SpecialItems;
import me.sora819.specialitems.config.ConfigAdapter;
import me.sora819.specialitems.config.ConfigHandler;
import me.sora819.specialitems.config.LocalizationConfig;
import org.bukkit.ChatColor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocalizationHandler {
    public static ConfigAdapter defaultMessagesConfig = ConfigHandler.defaultMessagesConfig;
    public static ConfigAdapter messagesConfig = ConfigHandler.messagesConfig;

    public static String getRawMessage(String key) {
        return messagesConfig != null && messagesConfig.get(key) != null ?
                messagesConfig.get(key) :
                defaultMessagesConfig.get(key);
    }

    public static String getRawMessage(String key, Boolean includePrefix) {
        String message = messagesConfig != null && messagesConfig.get(key) != null ? messagesConfig.get(key) : defaultMessagesConfig.get(key);
        String prefix = ConfigHandler.defaultConfig.get("prefix");

        return includePrefix ? prefix + message : message;
    }

    public static String getMessage(String key) {
        return messagesConfig != null && messagesConfig.get(key) != null ?
                ChatColor.translateAlternateColorCodes('&', messagesConfig.get(key)) :
                ChatColor.translateAlternateColorCodes('&', defaultMessagesConfig.get(key));
    }

    public static String getMessage(String key, Boolean includePrefix) {
        String message = messagesConfig != null && messagesConfig.get(key) != null ? messagesConfig.get(key) : defaultMessagesConfig.get(key);
        String prefix = ConfigHandler.defaultConfig.get("prefix");

        return includePrefix ? ChatColor.translateAlternateColorCodes('&', prefix + message ) : ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void setLocale(String language) {
        ConfigHandler.defaultConfig.set("language", language);
        messagesConfig = new LocalizationConfig(language);
    }

    public static List<String> getLocales() {
        List<String> locales = new ArrayList<>();

        String[] localeFiles = (new File(SpecialItems.getInstance().getDataFolder()+"/locale")).list();
        String[] defaultLocales = {"en", "hu"};

        locales.addAll(Arrays.asList(defaultLocales));
        locales.addAll(
            Arrays.asList(
                localeFiles != null ?
                    Arrays.stream(localeFiles)
                        .map(name -> name.replace("locale_", "").replace(".yml", ""))
                        .filter(name -> !locales.contains(name))
                        .toArray(String[]::new) :
                    new String[0]));

        return locales;
    }
}
