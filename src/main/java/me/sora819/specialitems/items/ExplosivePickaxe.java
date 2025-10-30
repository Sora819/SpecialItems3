package me.sora819.specialitems.items;

import me.sora819.specialitems.SpecialItems;
import me.sora819.specialitems.utils.InventoryHelper;
import me.sora819.specialitems.utils.NBTManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ExplosivePickaxe implements ICustomItem, Listener {
    private final ThreadLocal<Boolean> skipping = ThreadLocal.withInitial(() -> false);

    @Override
    public String getID() {
        return "explosive_pickaxe";
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack base_item = new ItemStack(Material.NETHERITE_PICKAXE);

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
    public void onBlockBreak(BlockBreakEvent e) {
        if (skipping.get()) {
            return;
        }

        Player player = e.getPlayer();
        Block block = e.getBlock();

        Bukkit.getScheduler().runTask(SpecialItems.getInstance(), () -> {
            skipping.set(true);

            if (checkItem(e.getPlayer().getInventory().getItemInMainHand())) {
                player.getWorld().createExplosion(block.getX()+0.5, block.getY()+0.5, block.getZ()+0.5, 0);

                for (int x = block.getX()-1; x <= block.getX()+1; x++) {
                    for (int y = block.getY()-1; y <= block.getY()+1; y++) {
                        for (int z = block.getZ()-1; z <= block.getZ()+1; z++) {
                            if (block.getX() == x && block.getY() == y && block.getZ() == z) {
                                continue;
                            }

                            Block localBlock = player.getWorld().getBlockAt(x, y, z);

                            if (!canBreak(localBlock)) {
                                continue;
                            }

                            BlockBreakEvent event = new BlockBreakEvent(localBlock, player);
                            SpecialItems.getInstance().getServer().getPluginManager().callEvent(event);

                            if (event.isCancelled()) {
                                continue;
                            }

                            player.breakBlock(localBlock);
                        }
                    }
                }
            }
            skipping.set(false);
        });
    }

    public boolean canBreak(Block block) {
        Material material = block.getBlockData().getMaterial();
        if (material.isAir()) return false;

        switch (material) {
            case BEDROCK, BARRIER, END_PORTAL_FRAME,
                 END_PORTAL, NETHER_PORTAL, COMMAND_BLOCK,
                 CHAIN_COMMAND_BLOCK, REPEATING_COMMAND_BLOCK, REINFORCED_DEEPSLATE,
                 WATER, LAVA -> { return false; }
            default -> { return true; }
        }
    }
}
