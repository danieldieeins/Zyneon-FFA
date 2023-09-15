package live.nerotv.ffa.listeners;

import live.nerotv.ffa.apis.API;
import live.nerotv.ffa.managers.InventoryManager;
import live.nerotv.ffa.managers.ItemManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInventory implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() != null) {
            if (e.getClickedInventory() != p.getInventory()) {
                e.setCancelled(true);
            } else {
                if (e.getCurrentItem() != null) {
                    ItemStack item = e.getCurrentItem();
                    if (item.getType().equals(Material.ENDER_CHEST)) {
                        e.setCancelled(true);
                        InventoryManager.openMenu(p);
                    } else if (item.equals(ItemManager.Placeholder)) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100, 100);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPickupAttempt(PlayerAttemptPickupItemEvent e) {
        if(!(API.getUser(e.getPlayer().getUniqueId()).canBuild())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        if(!(API.getUser(e.getPlayer().getUniqueId()).canBuild())) {
            e.setCancelled(true);
        }
    }
}