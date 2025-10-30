package me.sora819.specialitems.items;

import me.sora819.specialitems.config.ConfigHandler;
import me.sora819.specialitems.localization.LocalizationHandler;
import me.sora819.specialitems.utils.CustomRecipe;
import me.sora819.specialitems.utils.NBTManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface ICustomItem {
    String getID();
    ItemStack getItemStack();
    default Integer getInterval() { return null; }

    default Set<CustomRecipe> getRecipes() throws Exception {
        Set<String> recipeIDs = ConfigHandler.recipesConfig.getKeys();

        recipeIDs = recipeIDs.stream()
                .filter(id -> ConfigHandler.recipesConfig.<String>get(id + ".result").equals(getID()) )
                .collect(Collectors.toSet());

        Set<CustomRecipe> recipes = new HashSet<>();

        for (String recipeID : recipeIDs) {
            if (recipeID == null) {
                continue;
            }

            ConfigurationSection recipeConfig = ConfigHandler.recipesConfig.get(recipeID);

            CustomRecipe recipe = new CustomRecipe(recipeID, recipeConfig);

            recipes.add(recipe);
        }

        return recipes;
    }



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
