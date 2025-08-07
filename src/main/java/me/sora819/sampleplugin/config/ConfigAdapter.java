package me.sora819.sampleplugin.config;

import java.util.Set;

public interface ConfigAdapter {
    void reload();
    void save();
    <T> T get(String path);
    <T> T get(String path, T default_value);
    <T> void set(String path, T value);
    boolean has(String path);
    Set<String> getKeys();
    Set<String> getKeys(String path);
}
