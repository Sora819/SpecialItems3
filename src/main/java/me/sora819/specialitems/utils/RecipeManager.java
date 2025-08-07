package me.sora819.specialitems.utils;

import me.sora819.specialitems.SpecialItems;
import me.sora819.specialitems.items.ICustomItem;
import me.sora819.specialitems.items.ItemRegistry;
import me.sora819.specialitems.localization.LocalizationHandler;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;

import java.util.HashMap;
import java.util.Map;

public class RecipeManager {
    static Map<String, Recipe> recipeMap = new HashMap<>();

    public static void registerRecipes() {
        unregisterRecipes();

        for (ICustomItem item : ItemRegistry.getItems()) {
            if (!item.<Boolean>getConfig("craftable")) {
                continue;
            }

            try {
                for (CustomRecipe recipe : item.getRecipes()) {
                    SpecialItems.getInstance().getServer().addRecipe(recipe.getRecipe());
                    recipeMap.put(recipe.getID(), recipe.getRecipe());
                    SpecialItems.getInstance().getLogger().info(LocalizationHandler.getMessage("success.recipe_load") + recipe.getID());
                }
            } catch (Exception e) {
                SpecialItems.getInstance().getLogger().info(LocalizationHandler.getMessage("error.recipe_load"));
                SpecialItems.getInstance().getLogger().info(e.getMessage());
            }
        }
    }

    public static void unregisterRecipes() {
        recipeMap.forEach((key, _) -> SpecialItems.getInstance().getServer().removeRecipe(new NamespacedKey(SpecialItems.getInstance(), key)));
    }
}
