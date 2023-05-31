package fr.paradise.feature.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import fr.paradise.feature.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class RegionManage {

    public static ProtectedRegion getRegionsByName(Player player,String name){
        World world = BukkitAdapter.adapt(player.getWorld());

        RegionManager regions = WorldGuard.getInstance().getPlatform().getRegionContainer().get(world);
        ProtectedRegion region = regions.getRegion(name);

        return region;
    }

    public static boolean isInRegion(Player player, String regionName) {

            com.sk89q.worldedit.util.Location location = BukkitAdapter.adapt(player.getLocation());
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(location);

            //Testé si une des regions dans le quelle se trouve le joueur est dans
            for (ProtectedRegion region : set) {
                if(region.getId().equalsIgnoreCase(regionName)){
                    return true;
                }
            }
        return false;
    }


    public static boolean isInRegion(Player player, List regionsTp) {
        com.sk89q.worldedit.util.Location location = BukkitAdapter.adapt(player.getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(location);

        //Testé si une des regions dans le quelle se trouve le joueur est dans
        for (ProtectedRegion region : set) {
            if(regionsTp.contains( region.getId() )){
                return true;
            }
        }
        return false;
    }

}



