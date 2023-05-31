package fr.paradise.feature.listeners.player;

import fr.paradise.feature.Main;
import fr.paradise.feature.data.PlayerDataManager;
import fr.paradise.feature.utils.Config;
import fr.paradise.feature.utils.RegionManage;
import fr.paradise.feature.utils.armorstand.PorteAuto;
import fr.paradise.feature.utils.armorstand.SystemPorte;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;
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


        //========= Systeme Détection porte =========
        List<Entity> allEntity = player.getWorld().getEntities();
        List<Entity> nearEntityO = player.getNearbyEntities(5, 5, 5);
        List<Entity> nearEntityC = player.getNearbyEntities(6, 6, 6);

        //Liste des ArmorStand near
        List<Entity> nearArmorStandCustom = nearEntityO.stream()
                .filter(entity -> entity.getType() == EntityType.ARMOR_STAND && PorteAuto.listPortes.containsKey(entity.getUniqueId()))
                .collect(Collectors.toList());
        //Liste des ArmorStand nearOver
        List<Entity> nearOverArmorStandCustom = allEntity.stream()
                .filter(entity -> entity.getType() == EntityType.ARMOR_STAND && PorteAuto.listPortes.containsKey(entity.getUniqueId()))
                .filter(entity -> !nearEntityC.contains(entity))
                .collect(Collectors.toList());

        for(Entity entity : nearArmorStandCustom) {
            PorteAuto porte = PorteAuto.listPortes.get(entity.getUniqueId());
            //Open First Porte
            SystemPorte.porteOpen(porte, entity);

            PorteAuto porteLink = PorteAuto.getPorteLink(porte);

            if(porteLink != null){

                List<Entity> nearLink = entity.getNearbyEntities(5, 5, 5);

                //Foreach entity next to the entity
                for(Entity entityLink : nearLink){
                    if(entityLink.getType().equals(EntityType.ARMOR_STAND) && entityLink.getUniqueId().equals(porteLink.uuid)){
                        //Open Second Porte
                        SystemPorte.porteOpen(porteLink, entityLink);
                    }
                }
            }
        }

        for(Entity entity : nearOverArmorStandCustom){
            PorteAuto porte = PorteAuto.listPortes.get(entity.getUniqueId());
            //Close First Porte
            SystemPorte.porteClose(porte, entity);

            PorteAuto porteLink = PorteAuto.getPorteLink(porte);

            if(porteLink != null){
                List<Entity> nearLink = entity.getNearbyEntities(5, 5, 5);

                //Foreach entity next to the entity
                for(Entity entityLink : nearLink){
                    if(entityLink.getType().equals(EntityType.ARMOR_STAND) && entityLink.getUniqueId().equals(porteLink.uuid)){
                        //Open Second Porte
                        SystemPorte.porteClose(porteLink, entityLink);
                    }
                }
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


    }

}
