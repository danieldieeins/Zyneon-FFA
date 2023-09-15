package live.nerotv.ffa;

import live.nerotv.ffa.apis.API;
import live.nerotv.ffa.apis.ConfigAPI;
import live.nerotv.ffa.commands.Build;
import live.nerotv.ffa.listeners.*;
import live.nerotv.ffa.managers.BroadcastManager;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static String PN() { return "§9FFA §8» §7"; }
    public static PluginManager PM = Bukkit.getPluginManager();
    private static Main instance;
    public static Main getInstance() { return instance; }

    @Override
    public void onLoad() {
        initConfig();
    }

    @Override
    public void onEnable() {
        instance = this;
        initConfig();
        API.initCommand("build",new Build());
        API.initListeners(new PlayerDeath());
        API.initListeners(new PlayerInteract());
        API.initListeners(new PlayerInventory());
        API.initListeners(new PlayerJoin());
        API.initListeners(new PlayerQuit());
        API.date = API.getTime();
        BroadcastManager.send();
        Bukkit.createWorld(new WorldCreator("FFA"));
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void initConfig() {
        //MYSQL
        ConfigAPI.checkEntry("Core.Settings.MySQL.enable",false,ConfigAPI.Config,ConfigAPI.CFG);
        ConfigAPI.checkEntry("Core.Settings.MySQL.host","localhost",ConfigAPI.Config,ConfigAPI.CFG);
        ConfigAPI.checkEntry("Core.Settings.MySQL.port","3307",ConfigAPI.Config,ConfigAPI.CFG);
        ConfigAPI.checkEntry("Core.Settings.MySQL.database","database",ConfigAPI.Config,ConfigAPI.CFG);
        ConfigAPI.checkEntry("Core.Settings.MySQL.username","user",ConfigAPI.Config,ConfigAPI.CFG);
        ConfigAPI.checkEntry("Core.Settings.MySQL.password","password",ConfigAPI.Config,ConfigAPI.CFG);

        //TABLIST
        ConfigAPI.checkEntry("Core.Tablist.Header"," \n §9 §8● §7Minecraft - aber mehr \n ",ConfigAPI.Config,ConfigAPI.CFG);
        ConfigAPI.checkEntry("Core.Tablist.Footer"," \n §9https://www.zyneonstudios.com \n §7sponsored by §ftube-hosting.de \n ",ConfigAPI.Config,ConfigAPI.CFG);

        ConfigAPI.saveConfig(ConfigAPI.Config,ConfigAPI.CFG);
    }

    public static void setPrefix(Player player) {
        String Name = player.getName();
        org.bukkit.scoreboard.Scoreboard Scoreboard = player.getScoreboard();
        if(Scoreboard.getTeam("03Spieler")==null) {
            Scoreboard.registerNewTeam("00000Team");
            Scoreboard.registerNewTeam("01Creator");
            Scoreboard.registerNewTeam("02Premium");
            Scoreboard.registerNewTeam("03Spieler");
            Scoreboard.getTeam("00000Team").setPrefix("§cTeam §8● §f");
            Scoreboard.getTeam("01Creator").setPrefix("§dCreator §8● §f");
            Scoreboard.getTeam("02Premium").setPrefix("§6Premium §8● §f");
            Scoreboard.getTeam("03Spieler").setPrefix("§7User §8● §f");
            Scoreboard.getTeam("00000Team").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("01Creator").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("02Premium").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("03Spieler").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("00000Team").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
            Scoreboard.getTeam("01Creator").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
            Scoreboard.getTeam("02Premium").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
            Scoreboard.getTeam("03Spieler").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
        }
        for(Player p:Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("zyneon.team")) {
                Scoreboard.getTeam("00000Team").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("00000Team").getPrefix() + Name);
            } else if (p.hasPermission("zyneon.creator")) {
                Scoreboard.getTeam("01Creator").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("01Creator").getPrefix() + Name);
            } else if (p.hasPermission("zyneon.premium")) {
                Scoreboard.getTeam("02Premium").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("02Premium").getPrefix() + Name);
            } else {
                Scoreboard.getTeam("03Spieler").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("03Spieler").getPrefix() + Name);
            }
        }
    }
}
