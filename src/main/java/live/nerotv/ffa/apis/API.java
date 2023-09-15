package live.nerotv.ffa.apis;

import live.nerotv.ffa.Main;
import live.nerotv.ffa.utils.Countdown;
import live.nerotv.ffa.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class API {

    public static boolean isStopping=false;
    public static HashMap<UUID, User> users = new HashMap();
    public static int RestartDay = getYearDay()+1;

    public static User getUser(UUID uuid) {
        if(users.containsKey(uuid)) {
            return users.get(uuid);
        } else {
            return new User(uuid);
        }
    }

    public static void switchServer(Player player, String serverName) {
        try {
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(byteArray);
            out.writeUTF("Connect");
            out.writeUTF(serverName);
            player.sendPluginMessage(Main.getInstance(), "BungeeCord", byteArray.toByteArray());
        } catch (Exception ignore) {}
    }

    public static void scheduledShutdown() {
        if(!API.isStopping) {
            API.isStopping = true;
            new Countdown(27, Main.getInstance()) {
                @Override
                public void count(int current) {
                    if (current < 11) {
                        int cur = current - 1;
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a subtitle \"§8...§7startet der Server neu§8!\"");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title \"§7In §e" + cur + " Sekunden§8...\"");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
                        }
                        if (current <= 6) {
                            Bukkit.broadcastMessage("§cWICHTIG§8: §7Serverneustart in §e" + cur + " Sekunden§8!");
                        }
                        if (current == 1) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                API.switchServer(all, "Lobby-1");
                            }
                        } else if (current == 0) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                API.switchServer(all, "Lobby-1");
                            }
                            Bukkit.shutdown();
                        }
                    } else {
                        if (current == 26) {
                            Bukkit.broadcastMessage("§cWICHTIG§8: §7Serverneustart in §e25 Sekunden§8!");
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
                            }
                        } else if (current == 21) {
                            Bukkit.broadcastMessage("§cWICHTIG§8: §7Serverneustart in §e20 Sekunden§8!");
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
                            }
                        } else if (current == 16) {
                            Bukkit.broadcastMessage("§cWICHTIG§8: §7Serverneustart in §e15 Sekunden§8!");
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
                            }
                        } else if (current == 11) {
                            Bukkit.broadcastMessage("§cWICHTIG§8: §7Serverneustart in §e10 Sekunden§8!");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a subtitle \"§8...§7startet der Server neu§8!\"");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title @a title \"§7In §e10 Sekunden§8...\"");
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
                            }
                        }
                    }
                }
            }.start();
        }
    }

    public static void setTablist() {
        for(Player all : Bukkit.getOnlinePlayers()) {
            all.setPlayerListHeader(ConfigAPI.CFG.getString("Core.Tablist.Header").replace("&", "§"));
            all.setPlayerListFooter(ConfigAPI.CFG.getString("Core.Tablist.Footer").replace("&", "§"));
            Main.setPrefix(all);
            //ITEMS
        }
    }

    public static String date;

    public static String getTime() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        return format.format(now);
    }

    public static int getYearDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static void checkForRestart() {
        if(getYearDay()==RestartDay) {
            if(!isStopping) {
                scheduledShutdown();
            }
        }
    }

    public static void setScoreboard(Player player) {
        Scoreboard board = player.getScoreboard();
        Objective boardContent = board.getObjective("zyneon");
        board.resetScores("zyneon");
        Score zyneon = boardContent.getScore("§fServer§7-§fIP§8:");
        boardContent.setDisplayName(API.animatedString());
        Score zyneonContent = boardContent.getScore("§ezyneonstudios.com");
        Score placeholder0 = boardContent.getScore("§0");
        Score placeholder2 = boardContent.getScore("§2");
        Score placeholder3 = boardContent.getScore("§3");
        Score rank = boardContent.getScore("§fRang§8:");
        Score rankContent = boardContent.getScore("§e"+rankName(player));
        Score time = boardContent.getScore("§fZeit§8:");
        Score timeContent = boardContent.getScore("§e"+API.date);
        placeholder0.setScore(9);
        time.setScore(8);
        timeContent.setScore(7);
        placeholder2.setScore(6);
        rank.setScore(5);
        rankContent.setScore(4);
        placeholder3.setScore(3);
        zyneon.setScore(2);
        zyneonContent.setScore(1);
        Main.setPrefix(player);
    }

    public static String rankName(Player player) {
        if(player.hasPermission("zyneon.leading")) {
            return "Leitung";
        } else if(player.hasPermission("zyneon.team")) {
            return "Team";
        } else if(player.hasPermission("zyneon.creator")) {
            return "Creator";
        } else if(player.hasPermission("zyneon.premium")) {
            return "Premium";
        } else {
            return "Spieler";
        }
    }

    public static void renewScoreboard(Player player) {
        ScoreboardManager sm = Bukkit.getScoreboardManager();
        player.setScoreboard(sm.getNewScoreboard());
        Scoreboard board = player.getScoreboard();
        if(board.getObjective("zyneon")==null) {
            board.registerNewObjective("zyneon", "zyneon");
        }
        Objective boardContent = board.getObjective("zyneon");
        boardContent.setDisplaySlot(DisplaySlot.SIDEBAR);
        setScoreboard(player);
    }

    public static void initCommand(String commandName, CommandExecutor command) {
        Bukkit.getConsoleSender().sendMessage("§f  -> §7Lade Command \"§e"+commandName+"§7\"...");
        Objects.requireNonNull(Main.getInstance().getCommand(commandName)).setExecutor(command);
    }

    public static void initListeners(Listener listener) {
        Bukkit.getConsoleSender().sendMessage("§f  -> §7Lade Listener in der Klasse \"§e"+listener.getClass().getSimpleName()+"§7\"...");
        Main.PM.registerEvents(listener,Main.getInstance());
    }

    public static int animatedState;
    public static String animatedString() {
        int state = animatedState;
        String string = "§fFFA";
        if(state == 1) {
            string = "§9§lF§fFA";
        } else if(state == 2) {
            string = "§fF§9§lF§fA";
        } else if(state == 3) {
            string = "§fFF§9§lA";
        }
        return string;
    }
}