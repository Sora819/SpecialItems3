package me.sora819.specialitems;

import me.sora819.specialitems.config.ConfigHandler;
import me.sora819.specialitems.items.ItemRegistry;
import me.sora819.specialitems.localization.LocalizationHandler;
import me.sora819.specialitems.utils.RecipeManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public final class SpecialItems extends JavaPlugin {
    private static SpecialItems instance;
    private static Timer timer;

    public SpecialItems() {
        instance = this;
    }

    @Override
    public void onEnable() {
        timer = new Timer();

        ConfigHandler.reloadConfigurations();
        setCommandExecutors();
        registerListeners();
        ItemRegistry.registerCustomItems();
        RecipeManager.registerRecipes();

        getLogger().info(LocalizationHandler.getMessage("plugin.enabled"));
    }

    @Override
    public void onDisable() {
        ItemRegistry.unregisterCustomItems();
        timer.cancel();
        getLogger().info(LocalizationHandler.getMessage("plugin.disabled"));
    }

    static public SpecialItems getInstance(){
        return instance;
    }

    static public void runAtIntervals(TimerTask task, int interval) {
        timer.scheduleAtFixedRate(task, 0, interval * 1000L);
    }

    /**
     * Dynamically try to set the CommandExecutors for the commands registered in plugin.yml
     */
    private void setCommandExecutors() {
        for (String cmdName : getDescription().getCommands().keySet()) {
            try {

                Class<?> clazz = Class.forName(
                    this.getClass().getPackage().getName() + ".commands." +
                    cmdName.substring(0, 1).toUpperCase() +
                    cmdName.substring(1) +
                    "Command");

                CommandExecutor executor = (CommandExecutor) clazz.getDeclaredConstructor().newInstance();
                TabCompleter tabCompleter = (TabCompleter) clazz.getDeclaredConstructor().newInstance();

                getCommand(cmdName).setExecutor(executor);
                getCommand(cmdName).setTabCompleter(tabCompleter);
                getLogger().info(LocalizationHandler.getMessage("success.command_load") + cmdName);

            } catch (Exception e) {
                getLogger().warning(LocalizationHandler.getMessage("error.command_load") + cmdName);
                getLogger().warning(e.toString());
            }
        }
    }

    /**
     * Dynamically try to register all event listeners
     */
    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        Reflections reflections = new Reflections(getClass().getPackage().getName());

        Set<Class<? extends Listener>> listenerClasses = reflections.getSubTypesOf(Listener.class);

        for (Class<? extends Listener> clazz : listenerClasses) {
            try {
                Listener listener = clazz.getDeclaredConstructor().newInstance();
                pluginManager.registerEvents(listener, this);
                getLogger().info(LocalizationHandler.getMessage("success.listener_load") + clazz.getSimpleName());
            } catch (Exception e) {
                getLogger().warning(LocalizationHandler.getMessage("error.listener_load") + clazz.getSimpleName());
                getLogger().warning(e.toString());
            }
        }
    }
}
