package me.sora819.sampleplugin.config;

import me.sora819.sampleplugin.localization.LocalizationHandler;

public class ConfigHandler {
    public static ConfigAdapter defaultConfig = new DefaultConfig();
    public static ConfigAdapter defaultMessagesConfig = new LocalizationConfig("en");
    public static ConfigAdapter messagesConfig = new LocalizationConfig(defaultConfig.get("language"));

    public static void reloadConfigurations() {
        defaultConfig.reload();
        defaultMessagesConfig.reload();

        messagesConfig = new LocalizationConfig(defaultConfig.get("language"));
        LocalizationHandler.messagesConfig = messagesConfig;
    }
}
