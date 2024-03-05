package fr.paradise.feature.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;

public class PlayerOpenCloseInv implements Listener {

    @EventHandler
    public void onOpenChest(InventoryOpenEvent event){
        Player player = (Player) event.getPlayer();
        InventoryView invView = event.getView();
        event.getView().getOriginalTitle();

        if(invView.getType().equals(InventoryType.CHEST) || invView.getType().equals(InventoryType.ENDER_CHEST)){

            if(invView.getOriginalTitle().equals("Coffre")){
                player.sendTitle("Coffre", "", 0, 300*20, 0);
            } else if(invView.getOriginalTitle().equals("Coffre de l'Ender")){
                player.sendTitle("Coffre de l'Ender", "", 0, 300*20, 0);
            } else if(invView.getOriginalTitle().equals("Grand coffre")){
                player.sendTitle("Grand Coffre", "", 0, 300*20, 0);
            }
        }



    }

    @EventHandler
    public void onCloseChest(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        InventoryType invType = event.getInventory().getType();

        if(invType.equals(InventoryType.CHEST) ){
            player.sendTitle("Close Inventory", "", 0, 10, 0);
        }
    }

}