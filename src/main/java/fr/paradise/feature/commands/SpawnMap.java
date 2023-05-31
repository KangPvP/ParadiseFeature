package fr.paradise.feature.commands;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.paradise.feature.Main;
import fr.paradise.feature.utils.RegionManage;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SpawnMap implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        Location locPlayer = player.getLocation();

        if(args.length == 0){ //message Help Utilisation
            player.sendMessage("Cette commande fonction comme ceci:");
            return false;

        } else if (args.length == 1) {
            //==============================================================\\
            //                    CMD /spawnmap set
            if(args[0].equalsIgnoreCase("set")) {
                UUID id = UUID.randomUUID();
                Main.getSpawnMap().spawnpoints.put(id, locPlayer);

                player.sendMessage("Vous avez ajouté un nouveau SpawnPoint");

                //==============================================================\\
                //                   CMD /spawnmap remove
            } else if (args[0].equalsIgnoreCase("remove")){
                UUID id = Main.getSpawnMap().getSpawnPointFromLoc(locPlayer);

                if(id == null){
                    player.sendMessage("Vous n'êtes pas sur un points de SpawnPoint");
                }

                Main.getSpawnMap().spawnpoints.remove(id);
                player.sendMessage("Vous n'êtes pas sur un points de SpawnPoint");

                //==============================================================\\
                //                   CMD /spawnmap show
            } else if (args[0].equalsIgnoreCase("show")){
                player.sendMessage("Vous avez affiché les lieux de spawn durant 25s");

                new BukkitRunnable(){
                    int stop = 50;

                    @Override
                    public void run() {
                        // What you want to schedule goes here
                        stop--;

                        for(HashMap.Entry<UUID, Location> entry : Main.getSpawnMap().spawnpoints.entrySet()){
                            Location loc = entry.getValue();

                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(0, 127, 255), 1.0F);

                            loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 50, dustOptions);
                        }

                        if(stop == 0){
                            this.cancel();
                        }
                    }
                }.runTaskTimer(Main.getInstance() , 0, 10);
            }
            //==============================================================\\
            //                   CMD /spawnmap addzone [name]
        } else if(args.length == 2){
             if (args[0].equalsIgnoreCase("addzone")){
                 String nameRegion = args[1];
                 ProtectedRegion region = RegionManage.getRegionsByName(player, nameRegion);

                 if(region == null){
                     player.sendMessage(args[1] +  " Cette region n'existe pas");
                     return false;
                 }

                 Main.getSpawnMap().regionsZoneTp.add(nameRegion);

                 player.sendMessage("Vous avez ajouté la région " + nameRegion + "comme zone de Tp");

                 //==============================================================\\
                 //                   CMD /spawnmap removezone [name]
             } else if (args[0].equalsIgnoreCase("removezone")){
                 String nameRegion = args[1];

                 if(Main.getSpawnMap().regionsZoneTp.contains(nameRegion)){
                     Main.getSpawnMap().regionsZoneTp.remove(nameRegion);
                     player.sendMessage("Vous avez remove la région " + nameRegion + " des zone de Tp" );
                 } else {
                     player.sendMessage("Cette région n'est pas une region de considéré comme une zone de Tp");
                 }
             }
        }

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            List<String> action = new ArrayList<>();
            action.add("set");
            action.add("remove");
            action.add("show");
            action.add("addzone");
            action.add("removezone");
            return action;

        }

        return null;
    }
}


