package fr.paradise.feature.listeners.player;

import fr.paradise.feature.utils.CreateItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerSwapHand implements Listener {
    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent event) {
        ItemStack item = event.getOffHandItem();
        ItemStack phone = CreateItems.itemsCreated.get("phone");

        if(item.equals(phone)) {
            event.setCancelled(true);
        }
    }

}
