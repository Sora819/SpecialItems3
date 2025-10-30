package me.sora819.specialitems.items;

import me.sora819.specialitems.SpecialItems;
import me.sora819.specialitems.config.ConfigHandler;
import me.sora819.specialitems.utils.InventoryHelper;
import me.sora819.specialitems.utils.NBTManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.TimerTask;

public class RepairTalisman extends TimerTask implements ICustomItem {

    @Override
    public String getID() {
        return "repair_talisman";
    }
    public Integer getInterval() { return ConfigHandler.itemsConfig.get(getID() + ".interval"); }
    public Integer getRepairAmount() { return ConfigHandler.itemsConfig.get(getID() + ".repair_amount"); }
    public Boolean isEffectStackable() {return  ConfigHandler.itemsConfig.get(getID() + ".effect_stackable"); }

    @Override
    public ItemStack getItemStack() {
        ItemStack base_item = new ItemStack(Material.CLOCK);

        ItemMeta meta = base_item.getItemMeta();

        if (meta != null) {
            meta.setItemName(getName());
            meta.setLore(getLore());
            meta.setMaxStackSize(1);
        }

        base_item.setItemMeta(meta);

        NBTManager.setNBT(base_item, "custom_item", getID());

        return base_item;
    }

    @Override
    public void run() {
        Collection<? extends Player> players = SpecialItems.getInstance().getServer().getOnlinePlayers();

        for (Player player : players) {
            PlayerInventory inventory = player.getInventory();

            if (InventoryHelper.isItemPresent(this, inventory)) {
                int talismanCount =  InventoryHelper.getItemCount(this, inventory);

                for (int i = 0; i < inventory.getSize(); i++) {
                    if (i == inventory.getHeldItemSlot()) {
                        continue;
                    }

                    ItemStack item = inventory.getItem(i);
                    repairItem(item, talismanCount);
                }
            }
        }
    }

    public void repairItem(ItemStack itemStack, int talismanCount) {
        if (itemStack == null) {
            return;
        }

        Damageable meta = (Damageable) itemStack.getItemMeta();

        if (meta != null && meta.hasDamage()) {
            meta.setDamage( meta.getDamage() - (getRepairAmount() * (isEffectStackable() ? talismanCount : 1)));
        }

        itemStack.setItemMeta(meta);
    }
}
