package me.sora819.specialitems.items;

import me.sora819.specialitems.SpecialItems;
import me.sora819.specialitems.utils.NBTManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class FlyingBoots implements ICustomItem, Listener {

    @Override
    public String getID() {
        return "flying_boots";
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

        NBTManager.setNBT(base_item, "custom_item", getID());

        return base_item;
    }

    @EventHandler
    public void onEquipArmorByInteract(PlayerInteractEvent e) {
        e.getPlayer().setAllowFlight(checkItem(e.getPlayer().getInventory().getBoots()));
    }

    @EventHandler
    public void onEquipArmorFromInventory(InventoryClickEvent e) {
        Bukkit.getScheduler().runTask(SpecialItems.getInstance(), () -> {
            Player player = (Player)e.getWhoClicked();
            player.setAllowFlight(checkItem(player.getInventory().getBoots()));
        });
    }

    @EventHandler
    public void onEquipArmorFromDispenser(BlockDispenseArmorEvent e) {
        Player player = (Player)e.getTargetEntity();
        player.setAllowFlight(checkItem(player.getInventory().getBoots()));
    }

    @EventHandler
    public void onPlayerDeath(PlayerRespawnEvent e) {
        e.getPlayer().setAllowFlight(checkItem(e.getPlayer().getInventory().getBoots()));
    }
}
