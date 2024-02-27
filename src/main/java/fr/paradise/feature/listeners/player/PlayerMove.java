package fr.paradise.feature.listeners.player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import fr.paradise.feature.Main;
import fr.paradise.feature.data.PlayerDataManager;
import fr.paradise.feature.utils.CommandsHelper;
import fr.paradise.feature.utils.Config;
import fr.paradise.feature.utils.RegionManage;
import fr.paradise.feature.utils.armorstand.PorteAuto;
import fr.paradise.feature.utils.armorstand.SystemPorte;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlayerMove implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        //========= Systeme Dead Paradise =========
        int deadStatus = PlayerDataManager.getData(player).getDeadStatus();
        if(deadStatus == 0 || deadStatus == 1){

            int status = PlayerDataManager.getData(player).getDeadStatus();

            if(status == 0){
                if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getZ() != event.getTo().getZ() || event.getFrom().getYaw() != event.getTo().getYaw() || event.getFrom().getPitch() != event.getTo().getPitch()) {
                    Location loc = new Location(event.getFrom().getWorld(), event.getFrom().getX(), event.getTo().getY(), event.getFrom().getZ(), 0,90);
                    player.teleport(loc);
                }
            } else {
                event.setCancelled(true);
            }
        }

        if(RegionManage.isInRegion(player, Main.getSpawnMap().regionsZoneTp)){ //Player in a zoneTp
            //if le player entre dans la zone
            if(!Main.getSpawnMap().playersInZoneTp.containsKey(player)){
                Main.getSpawnMap().playersInZoneTp.put(player, System.currentTimeMillis() );
                player.sendMessage(Config.getColored("spawnmap.messages.enter"));
            }
        } else { //Player out a zoneTp
            //if player sort d'une zone
            if(Main.getSpawnMap().playersInZoneTp.containsKey(player)){
                Main.getSpawnMap().playersInZoneTp.remove(player);
                player.sendMessage(Config.getColored("spawnmap.messages.exit"));
            }
        }

        //   BarInfoMap
        Location from = event.getFrom();
        Location to = event.getTo();

        assert to != null;
        if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()){

            List<String> nameRegions = Config.getList("barinfomap.regionsname");

            assert nameRegions != null;
            for(String nameRegion : nameRegions){
                if(RegionManage.isInRegion(player, nameRegion)){

                }
            }


        }






    }
}
