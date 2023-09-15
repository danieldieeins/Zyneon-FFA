package live.nerotv.ffa.managers;

import live.nerotv.ffa.apis.API;
import live.nerotv.ffa.utils.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemManager {

    public static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore((List) Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack Placeholder = createGuiItem(Material.BLACK_STAINED_GLASS_PANE,"§0");

    public static void getItems(Player player) {
        User u = API.getUser(player.getUniqueId());
        player.getInventory().clear();
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.setUnbreakable(true);
        helmet.setItemMeta(helmetMeta);
        ItemMeta chestplateMeta = chestplate.getItemMeta();
        chestplateMeta.setUnbreakable(true);
        chestplate.setItemMeta(chestplateMeta);
        ItemMeta leggingsMeta = leggings.getItemMeta();
        leggingsMeta.setUnbreakable(true);
        leggings.setItemMeta(leggingsMeta);
        ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.setUnbreakable(true);
        boots.setItemMeta(bootsMeta);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.setUnbreakable(true);
        sword.setItemMeta(swordMeta);
        ItemStack arrow = new ItemStack(Material.ARROW);
        arrow.setAmount(3);
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.setUnbreakable(true);
        bow.setItemMeta(bowMeta);
        player.getInventory().addItem(sword,bow,new ItemStack(Material.GOLDEN_APPLE),arrow);
        player.getInventory().setItem(8,menuItem(u));
    }

    public static ItemStack menuItem(User u) {
        ItemStack item = new ItemStack(Material.ENDER_CHEST);
        ItemMeta itemMeta = item.getItemMeta();
        if(u.isBedrock()) {
            itemMeta.setDisplayName("§aAktionsmenü §8(§7Shop§8)");
        } else {
            itemMeta.setDisplayName("§bShop");
        }
        item.setItemMeta(itemMeta);
        return item;
    }
}