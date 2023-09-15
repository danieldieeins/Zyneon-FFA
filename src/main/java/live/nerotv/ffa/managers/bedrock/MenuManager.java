package live.nerotv.ffa.managers.bedrock;

import live.nerotv.ffa.apis.API;
import live.nerotv.ffa.managers.InventoryManager;
import live.nerotv.ffa.utils.User;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

public class MenuManager {

    public static void openMenu(Player player) {
        User u = API.getUser(player.getUniqueId());
        if(u.isBedrock()) {
            FloodgatePlayer f = FloodgateApi.getInstance().getPlayer(player.getUniqueId());
            f.sendForm(SimpleForm.builder().title("§9Aktionsmenü").content("§7Wähle aus, was du tun willst:").button("§9Shop öffnen").button("§8Zurück in die Lobby").button("§8Schließen").responseHandler((form,responseData)->{
                ButtonComponent button = form.parseResponse(responseData).getClickedButton();
                String buttonText = button.getText();
                if(buttonText.contains("Shop")) {
                    InventoryManager.openShop(player);
                } else if(buttonText.contains("Lobby")) {
                    API.switchServer(player,"Lobby-1");
                }
            }));
        } else {
            InventoryManager.openShop(player);
        }
    }
}