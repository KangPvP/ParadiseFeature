package fr.paradise.feature.listeners.player;

import org.bukkit.Material;
import org.bukkit.block.Block;

import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.lang.reflect.Field;


public class PlayerPlace implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Block block = event.getBlock();
        event.getItemInHand();

        System.out.println(block.getBlockData().getAsString());
        if(block.getState() instanceof Chest){
            Chest chest = (Chest) block.getState();
            chest.setCustomName("BB inv");

        }

    }
}

