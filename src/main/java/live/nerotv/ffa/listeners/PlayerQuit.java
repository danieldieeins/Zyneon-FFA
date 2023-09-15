package live.nerotv.ffa.listeners;

import live.nerotv.ffa.apis.API;
import live.nerotv.ffa.utils.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        p.setExp(0);
        p.setLevel(0);
        p.setFlying(false);
        p.setAllowFlight(false);
        User u = API.getUser(p.getUniqueId());
        e.setQuitMessage("§8« §c"+p.getName());
        u.destroy();
    }
}