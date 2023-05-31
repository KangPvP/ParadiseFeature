package fr.paradise.feature.listeners.player;

import fr.paradise.feature.Main;
import fr.paradise.feature.utils.CommandsHelper;
import fr.paradise.feature.utils.Config;
import fr.paradise.feature.utils.CreateItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.AIR) {
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("%player%", player.getName());

        ItemStack phone = CreateItems.itemsCreated.get("phone");

        if (item.equals(phone)) {
            CommandsHelper.executeAll(Config.getList("items.phone.commands"), params);
            event.setCancelled(true);
        }


    }

}
