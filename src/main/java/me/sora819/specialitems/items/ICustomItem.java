package me.sora819.specialitems.items;

import me.sora819.specialitems.config.ConfigHandler;
import me.sora819.specialitems.localization.LocalizationHandler;
import me.sora819.specialitems.utils.NBTManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.List;

public interface ICustomItem {
    String getID();
    ItemStack getItemStack();
    List<Recipe> getRecipes();

    default String getName() {
        return LocalizationHandler.getMessage("item." + getID() + ".name");
    }

    default List<String> getLore() {
        return List.of(LocalizationHandler.getMessage("item.flying_boots.lore"));
    }

    default boolean checkItem(ItemStack item) {
        if (item == null) {
            return false;
        }

        String custom_id = NBTManager.getNBT(item, "custom_item");

        return custom_id != null && custom_id.equalsIgnoreCase(getID());
    }

    default <T> T getConfig(String path) {
        return ConfigHandler.itemsConfig.get(getID() + "." + path);
    }
}
