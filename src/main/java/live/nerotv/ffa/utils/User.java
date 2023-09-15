package live.nerotv.ffa.utils;

import live.nerotv.ffa.Main;
import live.nerotv.ffa.apis.API;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private OfflinePlayer offlinePlayer;
    private Player player;
    private UUID uuid;
    private int kills;
    private boolean isBedrock;
    private boolean canBuild;

    public User(UUID uuid) {
        if(API.users.containsKey(uuid)) {
            User u = API.users.get(uuid);
            offlinePlayer = u.getOfflinePlayer();
            player = u.getPlayer();
            this.uuid = uuid;
            kills = u.getKills();
            isBedrock = u.isBedrock();
            canBuild = u.canBuild();
            return;
        }
        kills = 0;
        offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(Bukkit.getPlayer(uuid)!=null) {
            player = Bukkit.getPlayer(uuid);
        } else {
            player = null;
        }
        this.uuid = uuid;
        if (Main.PM.getPlugin("floodgate") != null) {
            if (offlinePlayer.getName() != null) {
                if (offlinePlayer.getName().contains("*")) {
                    this.isBedrock = true;
                } else {
                    this.isBedrock = org.geysermc.floodgate.api.FloodgateApi.getInstance().isFloodgatePlayer(uuid);
                }
            } else {
                this.isBedrock = org.geysermc.floodgate.api.FloodgateApi.getInstance().isFloodgatePlayer(uuid);
            }
        } else {
            this.isBedrock = false;
        }
        API.users.put(uuid,this);
        canBuild = false;
    }

    public OfflinePlayer getOfflinePlayer() {
        return this.offlinePlayer;
    }

    public Player getPlayer() {
        return this.player;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public int getKills() {
        return kills;
    }

    public boolean isBedrock() {
        return this.isBedrock;
    }

    public boolean canBuild() {
        return canBuild;
    }

    public void sendRawMessage(String message) {
        if(player!=null) {
            player.sendMessage(message.replace("&&","%AND%").replace("&","§").replace("%AND%","&"));
        }
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setCanBuild(boolean canBuild) {
        this.canBuild = canBuild;
    }

    public void sendMessage(String message) {
        sendRawMessage(Main.PN()+message);
        playSound(Sound.ENTITY_CHICKEN_EGG);
    }

    public void sendErrorMessage(String message) {
        sendRawMessage("§c"+message);
        playSound(Sound.BLOCK_ANVIL_BREAK);
    }

    public void sendActionbar(String message) {
        if(this.player!=null) {
            this.player.sendActionBar(message.replace("&&","%and%").replace("&","§").replace("%and%","&"));
        }
    }

    public void playSound(Sound sound) {
        if(player!=null) {
            player.playSound(player.getLocation(),sound,100,100);
        }
    }

    public void destroy() {
        offlinePlayer = null;
        player = null;
        kills = 0;
        isBedrock = false;
        canBuild = false;
        API.users.remove(uuid);
        uuid = null;
        System.gc();
    }
}
