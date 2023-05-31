package fr.paradise.feature.listeners.player;

import fr.paradise.feature.utils.CreateItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerClickInv implements Listener {
    @EventHandler
    public void onClickInventory(InventoryClickEvent event){
        ItemStack item = event.getCurrentItem();
        ItemStack phone = CreateItems.itemsCreated.get("phone");

        if(item == null){
            return;
        }

        if(item.equals(phone) || event.getHotbarButton() == 8) {
            event.setCancelled(true);
        }
    }
}
