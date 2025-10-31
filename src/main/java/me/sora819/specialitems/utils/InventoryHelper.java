package me.sora819.specialitems.utils;

import me.sora819.specialitems.items.ICustomItem;
import me.sora819.specialitems.items.ItemRegistry;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;

public class InventoryHelper {
    public static boolean isItemPresent(String id, PlayerInventory inventory) {
        ICustomItem item = ItemRegistry.getItem(id);
        return isItemPresent(item, inventory);
    }

    public static boolean isItemPresent(ICustomItem item, PlayerInventory inventory) {
        return Arrays.stream(inventory.getContents()).anyMatch(item::checkItem);
    }

    public static int getItemCount(String id, PlayerInventory inventory) {
        ICustomItem item = ItemRegistry.getItem(id);
        return getItemCount(item, inventory);
    }

    public static int getItemCount(ICustomItem item, PlayerInventory inventory) {
        return Arrays.stream(inventory.getContents()).filter(item::checkItem).mapToInt(ItemStack::getAmount).sum();
    }
}
