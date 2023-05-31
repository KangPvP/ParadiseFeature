package fr.paradise.feature.listeners.player;

import fr.paradise.feature.utils.CommandsHelper;
import fr.paradise.feature.utils.Config;
import fr.paradise.feature.utils.CreateItems;
import fr.paradise.feature.utils.armorstand.PorteAuto;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerInteractAtEntity implements Listener {

    @EventHandler
    public void OnInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();

        if (entity.getType() == EntityType.ARMOR_STAND) {

            if(PorteAuto.listPortes.containsKey(entity.getUniqueId())){
                event.setCancelled(true);
            }
        }

    }


}
