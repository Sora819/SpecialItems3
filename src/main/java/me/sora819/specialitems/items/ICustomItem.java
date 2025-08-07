package me.sora819.specialitems.items;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ICustomItem {
    String getName();
    String getID();
    List<String> getLore();
    ItemStack getItemStack();
    boolean checkItem(ItemStack item);
}
