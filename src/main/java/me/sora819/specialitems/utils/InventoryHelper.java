package me.sora819.specialitems.utils;

import me.sora819.specialitems.items.ICustomItem;
import me.sora819.specialitems.items.ItemRegistry;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class InventoryHelper {
    public static boolean isItemPresent(String id, PlayerInventory inventory) {
        ICustomItem item = ItemRegistry.getItem(id);
        return isItemPresent(item, inventory);
    }

    public static boolean isItemPresent(ICustomItem item, PlayerInventory inventory) {
        return inventory.contains(item.getItemStack());
    }
}
