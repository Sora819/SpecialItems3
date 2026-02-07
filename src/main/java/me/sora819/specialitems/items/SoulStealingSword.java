package me.sora819.specialitems.items;

import me.sora819.specialitems.SpecialItems;
import me.sora819.specialitems.localization.LocalizationHandler;
import me.sora819.specialitems.utils.NBTManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SoulStealingSword implements ICustomItem, Listener {
    private final ThreadLocal<Boolean> skipping = ThreadLocal.withInitial(() -> false);

    @Override
    public String getID() {
        return "soul_stealing_sword";
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack base_item = new ItemStack(Material.NETHERITE_SWORD);

        ItemMeta meta = base_item.getItemMeta();

        if (meta != null) {
            meta.setItemName(getName());
            List<String> lore = new ArrayList<>(getLore());
            lore.add(
                LocalizationHandler.getMessage("item." + getID() + ".kills")
                    .replace("{kills}", String.valueOf(0))
            );
            meta.setLore(lore);
        }

        base_item.setItemMeta(meta);

        NBTManager.setNBT(base_item, "custom_item", getID());

        return base_item;
    }

    @EventHandler
    public void onKillEntity(EntityDeathEvent e) {
        Player player = e.getEntity().getKiller();
        if (player == null) {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (!checkItem(item)) {
            return;
        }

        addKill(item);
        updateDamage(item);

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            List<String> lore = meta.getLore();

            if (lore != null && lore.size() >= 2) {
                lore.set(1,
                    LocalizationHandler.getMessage("item." + getID() + ".kills")
                        .replace("{kills}", String.valueOf(getKills(item))));
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
    }

    public void addKill(ItemStack item) {
        NBTManager.setNBT(item, "kills", getKills(item) + 1);
    }

    public int getKills(ItemStack item) {
        Integer kills = NBTManager.getNBT(item, "kills");
        return kills == null ? 0 : kills;
    }

    public double calculateDamageBonus(int kills) {
        return Math.log10(kills);
    }

    public void updateDamage(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        if (meta.getAttributeModifiers() == null) {
            meta.setAttributeModifiers(item.getType().getDefaultAttributeModifiers(EquipmentSlot.HAND));
        }

        if (meta.getAttributeModifiers() != null) {
            Collection<AttributeModifier> modifiers = meta.getAttributeModifiers(Attribute.ATTACK_DAMAGE);

            if (modifiers != null) {
                modifiers.stream()
                        .filter(attr -> attr.getKey().equals(new NamespacedKey(SpecialItems.getInstance(), getID())))
                        .forEach(attr -> {
                            meta.removeAttributeModifier(Attribute.ATTACK_DAMAGE, attr);
                        });
            }
        }

        meta.addAttributeModifier(
            Attribute.ATTACK_DAMAGE,
            new AttributeModifier(
                new NamespacedKey(SpecialItems.getInstance(), getID()),
                calculateDamageBonus(getKills(item)),
                AttributeModifier.Operation.MULTIPLY_SCALAR_1,
                EquipmentSlotGroup.MAINHAND
            )
        );

        item.setItemMeta(meta);
    }
}
