package me.sora819.specialitems.items;

import me.sora819.specialitems.SpecialItems;
import me.sora819.specialitems.config.ConfigHandler;
import me.sora819.specialitems.localization.LocalizationHandler;
import me.sora819.specialitems.utils.NBTManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MobCatcher implements ICustomItem, Listener {

    @Override
    public String getID() {
        return "mob_catcher";
    }
    public List<String> getEntityBlacklist() {return  ConfigHandler.itemsConfig.get(getID() + ".entity_blacklist"); }

    private final Set<UUID> suppressBlockInteract = ConcurrentHashMap.newKeySet();

    @Override
    public ItemStack getItemStack() {
        ItemStack base_item = new ItemStack(Material.END_CRYSTAL);
        ItemMeta meta = base_item.getItemMeta();

        if (meta != null) {
            meta.setItemName(getName());
            List<String> lore = new ArrayList<>(getLore());
            lore.add(
                    LocalizationHandler.getMessage("item." + getID() + ".no_entity_lore")
            );
            meta.setLore(lore);

            meta.setMaxStackSize(1);
        }

        base_item.setItemMeta(meta);

        NBTManager.setNBT(base_item, "custom_item", getID());

        return base_item;
    }

    public void updateLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        String entityType = NBTManager.getNBT(item, "entity.type");

        if (meta != null) {
            List<String> lore = meta.getLore();

            if (lore != null && lore.size() >= 2) {
                lore.set(1,
                       entityType != null ?
                            LocalizationHandler.getMessage("item." + getID() + ".entity_lore")
                                    .replace("{entity}", entityType) :
                            LocalizationHandler.getMessage("item." + getID() + ".no_entity_lore"));
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
    }

    private void suppressForOneTick(Player p) {
        UUID id = p.getUniqueId();
        suppressBlockInteract.add(id);

        Bukkit.getScheduler().runTask(SpecialItems.getInstance(), () -> suppressBlockInteract.remove(id));
    }

    @EventHandler
    public void onRightClickEntity(PlayerInteractEntityEvent e) {
        if (e.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!checkItem(item)) {
            return;
        }

        if (hasEntity(item)) {
            player.sendMessage(LocalizationHandler.getMessage("item." + getID() + ".already_has_entity"));
            return;
        }

        Entity entity = e.getRightClicked();

        if (!entity.isValid() || entity.isDead()) {
            return;
        }

        if (getEntityBlacklist().contains(entity.getType().name())) {
            return;
        }

        if (entity instanceof Player) {
            return;
        }

        storeEntity(item, entity);
        updateLore(item);
        suppressForOneTick(player);
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRightClickBlock(PlayerInteractEvent e) {
        if (e.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (!checkItem(item)) {
            return;
        }

        // Don't allow placing the item as a block
        e.setUseItemInHand(Event.Result.DENY);
        Bukkit.getScheduler().runTask(SpecialItems.getInstance(), player::updateInventory);

        UUID id = e.getPlayer().getUniqueId();
        if (suppressBlockInteract.contains(id)) {
            return;
        }

        if (!hasEntity(item)) {
            player.sendMessage(LocalizationHandler.getMessage("item." + getID() + ".dont_have_entity"));
            return;
        }

        // Don't allow interacting with anything, if there is an entity stored
        e.setUseInteractedBlock(Event.Result.DENY);
        e.setCancelled(true);

        Block clickedBlock = e.getClickedBlock();
        if (clickedBlock == null) return;

        BlockFace face = e.getBlockFace();
        Block targetBlock = clickedBlock.getRelative(face);

        if (!targetBlock.isPassable()) {
            return;
        }

        Location spawnLoc = targetBlock.getLocation().add(0.5, 0.0, 0.5);

        spawnEntity(item, spawnLoc);
        updateLore(item);
    }

    public void storeEntity(ItemStack item, Entity entity) {
        NBTManager.setNBT(item, "entity.type", entity.getType().name());
        NBTManager.setNBT(item, "entity.data", entity.createSnapshot().getAsString());

        entity.remove();
    }

    public void spawnEntity(ItemStack item, Location location) {
        String type = NBTManager.getNBT(item, "entity.type");
        String data = NBTManager.getNBT(item, "entity.data");

        if (type == null || data == null) {
            return;
        }

        EntitySnapshot snapshot = Bukkit.getEntityFactory().createEntitySnapshot(data);
        snapshot.createEntity(location);

        NBTManager.removeNBT(item, "entity.type");
        NBTManager.removeNBT(item, "entity.data");
    }

    public boolean hasEntity(ItemStack item) {
        return NBTManager.getNBT(item, "entity.type") != null;
    }

}
