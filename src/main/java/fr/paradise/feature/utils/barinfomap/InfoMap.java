package fr.paradise.feature.utils.barinfomap;

import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.paradise.feature.utils.RegionManage;
import org.bukkit.Bukkit;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.HashMap;

public class InfoMap {
    public static HashMap<ProtectedRegion, InfoMap> mapRegionBarInfoMap = new HashMap<ProtectedRegion, InfoMap>();
    public String nameRegion;
    public String displayNameRegion;
    public ArrayList<String> listCommands;

    public InfoMap(String name, String displayName, ArrayList<String> commands) {
        this.nameRegion = name;
        this.displayNameRegion = displayName;
        this.listCommands = commands;

        ProtectedRegion region = RegionManage.getRegionsByName((World) Bukkit.getWorld("gta"), name);

        InfoMap.mapRegionBarInfoMap.put(region, this);
    }





}
