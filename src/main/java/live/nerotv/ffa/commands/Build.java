package live.nerotv.ffa.commands;

import live.nerotv.ffa.apis.API;
import live.nerotv.ffa.utils.User;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Build implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(cmd.getName().equalsIgnoreCase("build")) {
            if(s instanceof Player) {
                Player p = (Player)s;
                if(p.hasPermission("zyneon.team")) {
                    User u = API.getUser(p.getUniqueId());
                    u.setCanBuild(!u.canBuild());
                    u.sendMessage("§7Dein §eBuild§8-§eMode§7 steht nun auf§8: §e"+u.canBuild());
                } else {
                    p.sendMessage("§cDas darfst du nicht§8!");
                    p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
                }
            } else {
                s.sendMessage("§cDazu musst du ein Spieler sein§8!");
            }
        }
        return false;
    }
}