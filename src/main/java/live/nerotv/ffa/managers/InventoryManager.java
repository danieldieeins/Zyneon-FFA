package live.nerotv.ffa.managers;

import live.nerotv.ffa.apis.API;
import live.nerotv.ffa.managers.bedrock.MenuManager;
import live.nerotv.ffa.utils.User;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryManager {

    public static void openMenu(Player player) {
        User u = API.getUser(player.getUniqueId());
        if(u.isBedrock()) {
            MenuManager.openMenu(player);
        } else {
            openShop(player);
        }
    }

    public static void openShop(Player player) {
        Inventory i = Bukkit.createInventory(null, InventoryType.HOPPER,"§bShop");
        i.setItem(0,ItemManager.Placeholder);
        i.setItem(1,ItemManager.Placeholder);
        i.setItem(2,ItemManager.Placeholder);
        i.setItem(3,ItemManager.Placeholder);
        i.setItem(4,ItemManager.Placeholder);
        player.playSound(player.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
        player.openInventory(i);
    }
}