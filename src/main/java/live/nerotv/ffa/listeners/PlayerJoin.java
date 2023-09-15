package live.nerotv.ffa.listeners;

import live.nerotv.ffa.apis.API;
import live.nerotv.ffa.managers.ItemManager;
import live.nerotv.ffa.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage(null);
        ItemManager.getItems(p);
        API.renewScoreboard(p);
        p.setInvulnerable(false);
        p.setFlying(false);
        p.setAllowFlight(false);
        p.setInvisible(false);
        API.setTablist();
        User u = API.getUser(p.getUniqueId());
        if(u.isBedrock()) {
            Bukkit.getConsoleSender().sendMessage("§e"+p.getName()+"§7 ist ein Bedrock-User§8!");
        }
        p.teleport(Bukkit.getWorld("FFA").getSpawnLocation());
        p.setExp(0);
        p.setLevel(0);
        p.setGameMode(GameMode.ADVENTURE);
        for(Player all:Bukkit.getOnlinePlayers()) {
            if(all.getUniqueId()!=p.getUniqueId()) {
                all.sendMessage("§8» §a"+p.getName());
            }
        }
    }
}