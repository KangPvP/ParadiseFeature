package fr.paradise.feature.listeners.world;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;


public class WorldBlockFrom implements Listener {

    @EventHandler
    public void onBlockForm(BlockFormEvent event) {

        if(event.getBlock().getType().equals(Material.COPPER_BLOCK) ){
            event.setCancelled(true);
        }else if(event.getBlock().getType().equals(Material.CUT_COPPER) ){
            event.setCancelled(true);
        }else if(event.getBlock().getType().equals(Material.CUT_COPPER_SLAB) ){
            event.setCancelled(true);
        }else if(event.getBlock().getType().equals(Material.CUT_COPPER_STAIRS) ){
            event.setCancelled(true);
        }else if(event.getBlock().getType().equals(Material.EXPOSED_COPPER) ){
            event.setCancelled(true);
        }else if(event.getBlock().getType().equals(Material.EXPOSED_CUT_COPPER) ){
            event.setCancelled(true);
        }else if(event.getBlock().getType().equals(Material.EXPOSED_CUT_COPPER_SLAB) ){
            event.setCancelled(true);
        }else if(event.getBlock().getType().equals(Material.EXPOSED_CUT_COPPER_STAIRS) ){
            event.setCancelled(true);
        }else if(event.getBlock().getType().equals(Material.WEATHERED_COPPER) ){
            event.setCancelled(true);
        }else if(event.getBlock().getType().equals(Material.WEATHERED_CUT_COPPER) ){
            event.setCancelled(true);
        }else if(event.getBlock().getType().equals(Material.WEATHERED_CUT_COPPER_SLAB) ){
            event.setCancelled(true);
        }else if(event.getBlock().getType().equals(Material.WEATHERED_CUT_COPPER_STAIRS) ){
            event.setCancelled(true);
        }
    }


}
