package fr.paradise.feature.utils.armorstand;

import fr.paradise.feature.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class PorteRunnable extends BukkitRunnable {
    @Override
    public void run() {
        for (Map.Entry<String, ArrayList<PorteAuto>> entry : PorteAuto.groupPortes.entrySet()) { // Pour chaque porte

            PorteAuto porteOne = entry.getValue().get(0);
            PorteAuto porteTwo = null;
            if(entry.getValue().size() >= 2){
                porteTwo = entry.getValue().get(1);
            }

            Location locDetection = porteOne.pointOfDetection();

            boolean playerNearPorte = false;  // Tester si un joueurs est proche de la porte

            for(Player player : Bukkit.getOnlinePlayers()){
                double distanceToDoor = locDetection.distance(player.getLocation());

                if (distanceToDoor < 4){ // Le joueurs est pas loin d'une porte
                    playerNearPorte = true;

                    List<Entity> listNearEntity = (List<Entity>) locDetection.getWorld().getNearbyEntities(locDetection, 4,4,4);

                    for(Entity entity : listNearEntity){
                        if( entity.getUniqueId().equals(porteOne.uuid) ){
                            if(porteOne.isOpen == false){
                                porteOne.getLocation().getWorld().playSound(porteOne.getLocation(), Objects.requireNonNull(Config.get("porte.soundopen")), (float) 0.5, 1);
                            }
                            SystemPorte.porteOpen(porteOne, entity); //Ouvrir la porte 1
                        }
                        if(porteTwo != null){
                            if(entity.getUniqueId().equals(porteTwo.uuid)){
                                SystemPorte.porteOpen(porteTwo, entity); //Ouvrir la porte 2
                            }
                        }
                    }
                }
            }

            if (playerNearPorte == false && porteOne.isOpen == true){   // Si aucun joueur proche et porte ouverte, fermer porte
                List<Entity> listNearEntity = (List<Entity>) locDetection.getWorld().getNearbyEntities(locDetection, 4,4,4);
                for(Entity entity : listNearEntity){
                    if( entity.getUniqueId().equals(porteOne.uuid) ){
                        if(porteOne.isOpen == true){
                            porteOne.getLocation().getWorld().playSound(porteOne.getLocation(), Objects.requireNonNull(Config.get("porte.soundclose")), (float) 0.5, 1);
                        }
                        SystemPorte.porteClose(porteOne, entity); //Fermer la porte 1
                    }
                    if(porteTwo != null){
                        if(entity.getUniqueId() == porteTwo.uuid){
                            SystemPorte.porteClose(porteTwo, entity); //Fermer la porte 2
                        }
                    }
                }
            }
        }
    }
}
