package me.sora819.specialitems.utils;

import me.sora819.specialitems.items.ICustomItem;
import me.sora819.specialitems.items.ItemRegistry;
import org.bukkit.inventory.PlayerInventory;

import java.util.concurrent.atomic.AtomicInteger;

public class InventoryHelper {
    public static boolean isItemPresent(String id, PlayerInventory inventory) {
        ICustomItem item = ItemRegistry.getItem(id);
        return isItemPresent(item, inventory);
    }

    public static boolean isItemPresent(ICustomItem item, PlayerInventory inventory) {
        return inventory.contains(item.getItemStack());
    }

    public static int getItemCount(String id, PlayerInventory inventory) {
        ICustomItem item = ItemRegistry.getItem(id);
        return getItemCount(item, inventory);
    }

    public static int getItemCount(ICustomItem item, PlayerInventory inventory) {
        AtomicInteger count = new AtomicInteger();

        inventory.forEach(itemStack -> {
            if (itemStack != null && item.checkItem(itemStack)) {
                count.set(count.get() + itemStack.getAmount());
            }
        });

        return count.get();
    }
}
