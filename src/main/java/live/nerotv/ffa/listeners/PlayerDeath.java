package live.nerotv.ffa.listeners;

import live.nerotv.ffa.Main;
import live.nerotv.ffa.apis.API;
import live.nerotv.ffa.managers.ItemManager;
import live.nerotv.ffa.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        e.setKeepInventory(true);
        e.setKeepLevel(true);
        Player p = e.getPlayer();
        p.getInventory().clear();
        p.setLevel(0);
        p.setExp(0);
        User pU = API.getUser(p.getUniqueId());
        pU.setKills(0);
        if(p.getKiller()!=null) {
            Player k = p.getKiller();
            ItemManager.getItems(k);
            k.setExp((float)0.99);
            User kU = API.getUser(k.getUniqueId());
            kU.setKills(kU.getKills()+1);
            k.setLevel(kU.getKills());
            k.playSound(k.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,100,100);
            Bukkit.broadcastMessage(Main.PN()+"§e"+p.getName()+"§7 wurde von §e"+k.getName()+"§7 getötet§8!");
            boolean isFive = kU.getKills() % 5 == 0;
            if(isFive) {
                Bukkit.broadcastMessage(Main.PN()+"§e"+k.getName()+"§7 hat eine Killstreak von §e"+kU.getKills()+"§8!");
            }
            p.setKiller(null);
        } else {
            Bukkit.broadcastMessage(Main.PN()+"§e"+p.getName()+"§7 ist gestorben§8!");
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        p.playSound(p.getLocation(),Sound.ENTITY_IRON_GOLEM_DEATH,80,50);
        p.setLevel(0);
        p.setExp((float)0);
        p.teleport(Bukkit.getWorld("FFA").getSpawnLocation());
        ItemManager.getItems(p);
    }
}