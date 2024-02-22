package fr.paradise.feature.utils.armorstand;

import fr.paradise.feature.utils.Config;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.Objects;
import java.util.UUID;

public class SystemPorte {

    public static void porteOpen(PorteAuto porte, Entity entity){

        if(!porte.isOpen){ //If porte Close
            if((porte.axe).equals("x")){
                entity.teleport(porte.getLocation().add(porte.actionToOpen,0,0));
            } else {
                entity.teleport(porte.getLocation().add(0,0, porte.actionToOpen));
            }

            porte.setIsOpen(true);
        }
    }
    public static void porteClose(PorteAuto porte, Entity entity){

        if(porte.isOpen){ //If porte Open
            if((porte.axe).equals("x")){
                entity.teleport(porte.getLocation());
            } else {
                entity.teleport(porte.getLocation());
            }
            porte.setIsOpen(false);
        }
    }
}
