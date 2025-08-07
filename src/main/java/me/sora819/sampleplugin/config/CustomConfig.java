package me.sora819.sampleplugin.config;

import me.sora819.sampleplugin.SamplePlugin;
import me.sora819.sampleplugin.localization.LocalizationHandler;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class CustomConfig implements ConfigAdapter{
    private final File targetFile;
    private final String sourceFile;
    private YamlConfiguration config;

    public CustomConfig(String fileName) {
        this(fileName, fileName);
    }


    public CustomConfig(String sourceFileName, String targetFileName) {
        this.targetFile = new File(SamplePlugin.getInstance().getDataFolder(), targetFileName);
        this.sourceFile = sourceFileName;

        if (!targetFile.exists()) {
            targetFile.getParentFile().mkdirs();
            SamplePlugin.getInstance().saveResource(sourceFile, false);
        }

        config = YamlConfiguration.loadConfiguration(targetFile);

        addDefaults();
    }

    private void addDefaults() {
        InputStream defaultFile = SamplePlugin.getInstance().getResource(sourceFile);

        if (defaultFile != null) {
            FileConfiguration defaults = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defaultFile, StandardCharsets.UTF_8));

            config.addDefaults(defaults);
            config.options().copyDefaults(true);
            save();
        }
    }

    @Override
    public void reload() {
        config = YamlConfiguration.loadConfiguration(targetFile);
        addDefaults();
    }

    @Override
    public void save() {
        try {
            config.save(targetFile);
        } catch (IOException e) {
            SamplePlugin.getInstance().getLogger().warning(LocalizationHandler.getMessage("error.save_custom_config"));
        }
    }

    @Override
    public <T> T get(String path) {
        return (T)config.get(path);
    }

    @Override
    public <T> T get(String path, T default_value) {
        return get(path) != null ? get(path) : default_value;
    }

    @Override
    public <T> void set(String path, T value) {
        config.set(path, value);
        save();
    }

    @Override
    public boolean has(String path) {
        return config.contains(path);
    }

    public Set<String> getKeys() {
        return config.getKeys(false);
    }

    public Set<String> getKeys(String path) {
        ConfigurationSection section = config.getConfigurationSection(path);

        if (section != null) {
            return section.getKeys(false);
        } else {
            return Set.of();
        }
    }
}
