package fr.paradise.feature.listeners.player;

import fr.paradise.feature.data.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommands implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();

        //Cancel Command if player is in mod DEAD
        if (PlayerDataManager.getData(player).getDeadStatus() != -1){
            e.setCancelled(true);
        }



    }

}
