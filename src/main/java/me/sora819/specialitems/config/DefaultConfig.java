package me.sora819.specialitems.config;

import me.sora819.specialitems.SpecialItems;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class DefaultConfig implements ConfigAdapter{
    public DefaultConfig() {
        SpecialItems.getInstance().saveDefaultConfig();

        addDefaults();
    }

    private void addDefaults() {
        InputStream defaultFile = SpecialItems.getInstance().getResource("config.yml");

        if (defaultFile != null) {
            FileConfiguration defaults = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defaultFile, StandardCharsets.UTF_8));

            SpecialItems.getInstance().getConfig().addDefaults(defaults);
            SpecialItems.getInstance().getConfig().options().copyDefaults(true);
            save();
        }
    }

    @Override
    public void reload() {
        SpecialItems.getInstance().reloadConfig();

        addDefaults();
    }

    @Override
    public void save() {
        SpecialItems.getInstance().saveConfig();
    }

    @Override
    public <T> T get(String path) {
        return (T) SpecialItems.getInstance().getConfig().get(path);
    }

    @Override
    public <T> T get(String path, T default_value) {
        return get(path) != null ? get(path) : default_value;
    }

    @Override
    public <T> void set(String path, T value) {
        SpecialItems.getInstance().getConfig().set(path, value);
        save();
    }

    @Override
    public boolean has(String path) {
        return SpecialItems.getInstance().getConfig().contains(path);
    }

    public Set<String> getKeys() {
        return SpecialItems.getInstance().getConfig().getKeys(false);
    }

    public Set<String> getKeys(String path) {
        ConfigurationSection section = SpecialItems.getInstance().getConfig().getConfigurationSection(path);

        if (section != null) {
            return section.getKeys(false);
        } else {
            return Set.of();
        }
    }
}
