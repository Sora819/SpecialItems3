package me.sora819.specialitems.items;

import me.sora819.specialitems.localization.LocalizationHandler;
import org.bukkit.Material;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class FlyingBoots implements ICustomItem {

    @Override
    public String getName() {
        return LocalizationHandler.getMessage("item.flying_boots.name");
    }

    @Override
    public String getID() {
        return "flying_boots";
    }

    @Override
    public List<String> getLore() {
        return List.of(LocalizationHandler.getMessage("item.flying_boots.lore"));
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack base_item = new ItemStack(Material.DIAMOND_BOOTS);

        ItemMeta meta = base_item.getItemMeta();

        if (meta != null) {
            meta.setItemName(getName());
            meta.setLore(getLore());
        }

        base_item.setItemMeta(meta);

        return base_item;
    }

    @Override
    public boolean checkItem(ItemStack item) {
        return false;
    }

    public void onEquipArmorByInteract(PlayerInteractEvent e) {

    }

    public void onEquipArmorFromInventory(InventoryClickEvent e) {

    }

    public void onEquipArmorFromDispenser(BlockDispenseArmorEvent e) {

    }

    public void onPlayerDeath(PlayerDeathEvent e) {

    }
}
