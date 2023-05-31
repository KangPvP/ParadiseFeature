package fr.paradise.feature.listeners.player;

import fr.paradise.feature.Main;
import fr.paradise.feature.utils.CreateItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDrop implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        ItemStack item = event.getItemDrop().getItemStack();
        ItemStack phone = CreateItems.itemsCreated.get("phone");

        Main.array.add(event.getPlayer().getUniqueId());

        if (item.equals(phone)) {
            event.setCancelled(true);
        }


    }

}
