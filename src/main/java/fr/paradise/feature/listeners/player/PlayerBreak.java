package fr.paradise.feature.listeners.player;

import fr.paradise.feature.Main;
import fr.paradise.feature.data.PlayerDataManager;
import fr.paradise.feature.utils.CommandsHelper;
import fr.paradise.feature.utils.Config;
import fr.paradise.feature.utils.armorstand.PorteAuto;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PlayerBreak implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if (event.getBlock().getType().equals(Material.ARMOR_STAND)) {
            PorteAuto porteAuto = PorteAuto.getPorteFromLoc( event.getBlock().getLocation() );
            if(porteAuto != null){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreakEntity(EntityDamageEvent event){
        if (event.getEntity().getType() == EntityType.ARMOR_STAND) {

            if(PorteAuto.listPortes.containsKey(event.getEntity().getUniqueId())){
                event.setCancelled(true);
            }
        }
    }

}



