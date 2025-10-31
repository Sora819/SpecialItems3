package me.sora819.specialitems.items;

import me.sora819.specialitems.SpecialItems;
import me.sora819.specialitems.config.ConfigHandler;
import me.sora819.specialitems.utils.InventoryHelper;
import me.sora819.specialitems.utils.NBTManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.concurrent.atomic.AtomicReference;

public class LuckyCharm implements ICustomItem, Listener {

    @Override
    public String getID() {
        return "lucky_charm";
    }
    public Boolean isEffectStackable() {return  ConfigHandler.itemsConfig.get(getID() + ".effect_stackable"); }

    @Override
    public ItemStack getItemStack() {
        ItemStack base_item = new ItemStack(Material.TOTEM_OF_UNDYING);

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
    public void onPlayerDeath(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        updateEffect(player);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player) {
            updateEffect((Player)e.getEntity());
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        updateEffect(e.getPlayer());
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player)(e.getWhoClicked());
        updateEffect(player);
    }

    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent e) {
        Player player = (Player)e.getWhoClicked();
        updateEffect(player);
    }

    @EventHandler
    public void onPlayerBlockBreak(BlockBreakEvent e) {
        if (InventoryHelper.isItemPresent(this, e.getPlayer().getInventory())) {
            ItemStack mainItem = e.getPlayer().getInventory().getItemInMainHand();

            AtomicReference<ItemMeta> meta = new AtomicReference<>(mainItem.getItemMeta());

            if (meta.get() != null) {
                meta.get().addEnchant(Enchantment.FORTUNE, meta.get().getEnchantLevel(Enchantment.FORTUNE) + getEffectMultiplier(e.getPlayer()), true);

                Bukkit.getScheduler().runTask(SpecialItems.getInstance(), () -> {
                    meta.set(mainItem.getItemMeta());
                    meta.get().addEnchant(Enchantment.FORTUNE, Math.max(meta.get().getEnchantLevel(Enchantment.FORTUNE) - getEffectMultiplier(e.getPlayer()), 0), true);
                    mainItem.setItemMeta(meta.get());
                });

                mainItem.setItemMeta(meta.get());
            }
        }
    }

    private int getEffectMultiplier(Player player) {
        return isEffectStackable() ? InventoryHelper.getItemCount(this, player.getInventory()) : 1;
    }

    private void updateEffect(Player player) {
        Bukkit.getScheduler().runTask(SpecialItems.getInstance(), () -> {
            PlayerInventory inventory = player.getInventory();

            if (InventoryHelper.isItemPresent(this, inventory)) {
                removeEffect(player);
                addEffect(player, getEffectMultiplier(player));
            } else {
                removeEffect(player);
            }
        });
    }

    private void addEffect(Player player, int multiplier) {
        AttributeInstance luckAttribute = player.getAttribute(Attribute.LUCK);
        luckAttribute.addModifier(
            new AttributeModifier(
                new NamespacedKey(SpecialItems.getInstance(), getID()),
                2.0 * multiplier,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.ANY
            )
        );
    }

    private void removeEffect(Player player) {
        AttributeInstance luckAttribute = player.getAttribute(Attribute.LUCK);
        luckAttribute.getModifiers()
            .stream()
            .filter(mod -> mod.getKey().equals(new NamespacedKey(SpecialItems.getInstance(), getID())))
            .forEach(luckAttribute::removeModifier);
    }
}
