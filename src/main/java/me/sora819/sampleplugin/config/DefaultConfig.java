package me.sora819.sampleplugin.config;

import me.sora819.sampleplugin.SamplePlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class DefaultConfig implements ConfigAdapter{
    public DefaultConfig() {
        SamplePlugin.getInstance().saveDefaultConfig();

        addDefaults();
    }

    private void addDefaults() {
        InputStream defaultFile = SamplePlugin.getInstance().getResource("config.yml");

        if (defaultFile != null) {
            FileConfiguration defaults = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defaultFile, StandardCharsets.UTF_8));

            SamplePlugin.getInstance().getConfig().addDefaults(defaults);
            SamplePlugin.getInstance().getConfig().options().copyDefaults(true);
            save();
        }
    }

    @Override
    public void reload() {
        SamplePlugin.getInstance().reloadConfig();

        addDefaults();
    }

    @Override
    public void save() {
        SamplePlugin.getInstance().saveConfig();
    }

    @Override
    public <T> T get(String path) {
        return (T)SamplePlugin.getInstance().getConfig().get(path);
    }

    @Override
    public <T> T get(String path, T default_value) {
        return get(path) != null ? get(path) : default_value;
    }

    @Override
    public <T> void set(String path, T value) {
        SamplePlugin.getInstance().getConfig().set(path, value);
        save();
    }

    @Override
    public boolean has(String path) {
        return SamplePlugin.getInstance().getConfig().contains(path);
    }

    public Set<String> getKeys() {
        return SamplePlugin.getInstance().getConfig().getKeys(false);
    }

    public Set<String> getKeys(String path) {
        ConfigurationSection section = SamplePlugin.getInstance().getConfig().getConfigurationSection(path);

        if (section != null) {
            return section.getKeys(false);
        } else {
            return Set.of();
        }
    }
}
