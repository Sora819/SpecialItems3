package me.sora819.specialitems.items;

import me.sora819.specialitems.SpecialItems;
import me.sora819.specialitems.utils.NBTManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FlyingBoots implements ICustomItem, Listener {

    @Override
    public String getID() {
        return "flying_boots";
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack base_item = new ItemStack(Material.NETHERITE_BOOTS);

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
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        e.getPlayer().setAllowFlight(checkItem(e.getPlayer().getInventory().getBoots()));
    }

    @EventHandler
    public void onEquipArmorFromInventory(InventoryClickEvent e) {
        // We have to wait for the next tick, so that we can check the new state of the inventory
        Bukkit.getScheduler().runTask(SpecialItems.getInstance(), () -> {
            Player player = (Player)e.getWhoClicked();

            if (player.getGameMode() == GameMode.CREATIVE) {
                return;
            }

            player.setAllowFlight(checkItem(player.getInventory().getBoots()));
        });
    }

    @EventHandler
    public void onEquipArmorFromDispenser(BlockDispenseArmorEvent e) {
        if (e.getTargetEntity() instanceof Player && ((Player)e.getTargetEntity()).getGameMode() == GameMode.CREATIVE) {
            return;
        }

        Player player = (Player)e.getTargetEntity();
        player.setAllowFlight(checkItem(player.getInventory().getBoots()));
    }

    @EventHandler
    public void onPlayerDeath(PlayerRespawnEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        e.getPlayer().setAllowFlight(checkItem(e.getPlayer().getInventory().getBoots()));
    }

    @EventHandler
    public void onArmorBreak(PlayerItemBreakEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (checkItem(e.getBrokenItem())) {
            e.getPlayer().setAllowFlight(checkItem(e.getPlayer().getInventory().getBoots()));
        }
    }
}
