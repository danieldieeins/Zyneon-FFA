package live.nerotv.ffa.listeners;

import live.nerotv.ffa.apis.API;
import live.nerotv.ffa.managers.InventoryManager;
import live.nerotv.ffa.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getItem() != null) {
            if (e.getItem().getType().equals(Material.ENDER_CHEST)) {
                e.setCancelled(true);
                InventoryManager.openMenu(p);
                return;
            }
        }
        if (e.getClickedBlock() != null) {
            if (!(API.getUser(e.getPlayer().getUniqueId()).canBuild())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(HangingBreakByEntityEvent e) {
        e.setCancelled(true);
        if(e.getRemover() instanceof Player) {
            Player p = (Player)e.getRemover();
            User u = API.getUser(p.getUniqueId());
            if(u.canBuild()) {
                e.setCancelled(false);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        Entity en = e.getRightClicked();
        if(!(en instanceof Player)) {
            User u = API.getUser(p.getUniqueId());
            e.setCancelled(!u.canBuild());
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                Location loc1 = p.getLocation();
                Location loc2 = Bukkit.getWorld("FFA").getSpawnLocation();
                if(loc1.distance(loc2)<=10) {
                    e.setCancelled(true);
                }
            }
        }
    }
}