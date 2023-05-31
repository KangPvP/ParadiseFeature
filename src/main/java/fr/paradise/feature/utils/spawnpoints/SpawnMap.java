package fr.paradise.feature.utils.spawnpoints;

import fr.paradise.feature.Main;
import fr.paradise.feature.utils.Config;
import fr.paradise.feature.utils.armorstand.PorteAuto;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class SpawnMap {

    public HashMap<UUID, Location> spawnpoints = new HashMap<>();
    public ArrayList<String> regionsZoneTp = new ArrayList<>();
    public HashMap<Player, Long> playersInZoneTp = new HashMap<>();

    public SpawnMap(){
        startScheduler();
    }

    public UUID getSpawnPointFromLoc(Location location) {
        for (HashMap.Entry<UUID, Location> entry : spawnpoints.entrySet()) { //ForEach value in HashMap
            UUID id = entry.getKey();
            Location locValue = entry.getValue().getBlock().getLocation();

            Location locBlc = location.getBlock().getLocation();

            if(locValue.getWorld().getName().equals(locBlc.getWorld().getName())){
                if(locValue.getX() == locBlc.getX()){
                    if(locValue.getY() == locBlc.getY()){
                        if(locValue.getZ() == locBlc.getZ()){
                            return id;
                        }
                    }
                }
            }
        }
        return null;
    }

    public ArrayList<UUID> spawnpointsRemove(ArrayList<UUID> spawnpoints){
        ArrayList<UUID> spawnPointRemove = new ArrayList<>();

        for (UUID id : spawnpoints) {
            if(! Main.getSpawnMap().spawnpoints.containsKey(id)){
                spawnPointRemove.add(id);
            }
        }
        return spawnPointRemove;
    }

    public ArrayList<UUID> spawnPointNoSave(ArrayList<UUID> spawnpoints){

        ArrayList<UUID> spawnPointNoSave = new ArrayList<>();

        for (HashMap.Entry<UUID, Location> entry : Main.getSpawnMap().spawnpoints.entrySet()) {
            UUID id = entry.getKey();

            if(!spawnpoints.contains(id)){
                spawnPointNoSave.add(id);
            }
        }
        return spawnPointNoSave;
    }

    public Location randLocSpawnPoint(){

        Set<UUID> keySet = Main.getSpawnMap().spawnpoints.keySet();
        List<UUID> spawnPointsId = new ArrayList<>(keySet);

        int size = spawnPointsId.size();
        int randIdx = new Random().nextInt(size);

        UUID id = spawnPointsId.get(randIdx);

        return Main.getSpawnMap().spawnpoints.get(id);
    }



    public void startScheduler(){
        new BukkitRunnable() {
            @Override
            public void run() {
                long timeActuel = System.currentTimeMillis();

                for (HashMap.Entry<Player, Long> entry :  playersInZoneTp.entrySet()) {
                    Player player = entry.getKey();

                    long time = entry.getValue();

                    int timeTp = Config.getInt("spawnmap.timetp");

                    if (timeActuel - time > timeTp*1000) {
                        Location locSpawnPoint = randLocSpawnPoint();
                        Main.getSpawnMap().playersInZoneTp.remove(player);
                        player.teleport(locSpawnPoint);
                        player.sendMessage(Config.getColored("spawnmap.messages.tp-complet"));
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 40,20);
    }








}
