package fr.paradise.feature.listeners.player;

import fr.paradise.feature.Main;
import fr.paradise.feature.utils.CreateItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {
    @EventHandler
    public void onDeath(PlayerRespawnEvent event){
        Player player = event.getPlayer();

        player.getInventory().setItem(8, CreateItems.itemsCreated.get("phone"));
    }

}
