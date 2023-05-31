package fr.paradise.feature.listeners.player;

import fr.paradise.feature.utils.CommandsHelper;
import fr.paradise.feature.utils.Config;
import fr.paradise.feature.utils.CreateItems;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerInteractEntity implements Listener {

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event){

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(event.getRightClicked().getType() == EntityType.ITEM_FRAME){
            HashMap<String, String> params = new HashMap<>();
            params.put("%player%", player.getName());

            ItemStack phone = CreateItems.itemsCreated.get("phone");

            if (item.equals(phone)) {
                event.setCancelled(true);
                CommandsHelper.executeAll(Config.getList("items.phone.commands"), params);
            }

        }


    }

}
