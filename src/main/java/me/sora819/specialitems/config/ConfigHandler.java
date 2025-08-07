package me.sora819.specialitems.config;

import me.sora819.specialitems.localization.LocalizationHandler;

public class ConfigHandler {
    public static ConfigAdapter defaultConfig = new DefaultConfig();
    public static ConfigAdapter defaultMessagesConfig = new LocalizationConfig("en");
    public static ConfigAdapter messagesConfig = new LocalizationConfig(defaultConfig.get("language"));
    public static ConfigAdapter recipesConfig = new CustomConfig("recipes.yml");
    public static ConfigAdapter itemsConfig = new CustomConfig("items.yml");

    public static void reloadConfigurations() {
        defaultConfig.reload();
        defaultMessagesConfig.reload();
        recipesConfig.reload();
        itemsConfig.reload();

        messagesConfig = new LocalizationConfig(defaultConfig.get("language"));
        LocalizationHandler.messagesConfig = messagesConfig;
    }
}
