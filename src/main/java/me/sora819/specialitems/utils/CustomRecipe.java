package me.sora819.specialitems.utils;

import me.sora819.specialitems.SpecialItems;
import me.sora819.specialitems.items.ItemRegistry;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class CustomRecipe {
    private final Recipe recipe;
    private final String id;

    public CustomRecipe(String recipeID, ConfigurationSection recipeConfig) throws Exception {
        id = recipeID;
        String recipeType = recipeConfig.getString("type");

        recipe = switch (recipeType) {
            case "shaped" -> createShapedRecipe(recipeID, recipeConfig);
            case "shapeless" -> createShapelessRecipe(recipeID, recipeConfig);
            case null -> createShapedRecipe(recipeID, recipeConfig);
            default -> createShapedRecipe(recipeID, recipeConfig);
        };
    }

    private ShapedRecipe createShapedRecipe(String recipeID, ConfigurationSection recipeConfig) throws Exception {
        ShapedRecipe recipe = new ShapedRecipe(
                new NamespacedKey(SpecialItems.getInstance(), recipeID),
                ItemRegistry.getItem(recipeConfig.getString("result")).getItemStack());

        recipe.shape(recipeConfig.getStringList("shape").toArray(String[]::new));

        ConfigurationSection ingredientsConfig = recipeConfig.getConfigurationSection("ingredients");

        if (ingredientsConfig == null) {
            throw new Exception("Invalid ingredients config");
        }

        for (String key : ingredientsConfig.getKeys(false)) {
            String ingredientName = ingredientsConfig.getString(key);
            if (ingredientName == null) {
                throw new Exception("Invalid ingredient name");
            }
            Material material = Material.getMaterial(ingredientName);
            if (material == null) {
                throw new Exception("Invalid ingredient material");
            }
            recipe.setIngredient(key.charAt(0), material);
        }

        return recipe;
    }

    private ShapelessRecipe createShapelessRecipe(String recipeID, ConfigurationSection recipeConfig) throws Exception {
        ShapelessRecipe recipe = new ShapelessRecipe(
                new NamespacedKey(SpecialItems.getInstance(), recipeID),
                ItemRegistry.getItem(recipeConfig.getString("result")).getItemStack());

        for (String ingredientName : recipeConfig.getStringList("ingredients")) {
            Material material = Material.getMaterial(ingredientName);
            if (material == null) {
                throw new Exception("Invalid ingredient material");
            }
            recipe.addIngredient(material);
        }

        return recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public String getID() {
        return id;
    }
}
