package fr.paradise.feature.listeners.player;

import fr.paradise.feature.Main;
import fr.paradise.feature.data.PlayerDataManager;
import fr.paradise.feature.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();

        event.setQuitMessage(Config.getColored("welcome.leave").replace("%player%", player.getName()));

        Main.getInstance().nbPlayer = Main.getInstance().nbPlayer - 1;

        //Save bug spectator Dead
        int status = PlayerDataManager.getData(player).getDeadStatus();
        if(status == 0 || status == 1){
            player.teleport(Main.getInstance().spawnLoc);
            player.setGameMode(GameMode.SURVIVAL);
            PlayerDataManager.getData(player).removePlayerDeadCine();
        }

    }

}
