package fr.paradise.feature.listeners.player;

import fr.paradise.feature.Main;
import fr.paradise.feature.data.PlayerDataManager;
import fr.paradise.feature.utils.CommandsHelper;
import fr.paradise.feature.utils.Config;
import fr.paradise.feature.utils.CreateItems;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PiglinAbstract;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event){


        new BukkitRunnable() {
            final Player player = event.getEntity();
            final Player killer = player.getKiller();

            final Vector levitationVector = new Vector(0, 0.05, 0);
            final Location loc = player.getLocation();
            final Location deathLocation = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 0,90);
            int ticks = 0;

            public void run() {
                if (ticks > 2 && ticks <= 100) {

                    Block block = player.getLocation().add(0,2,0).getBlock();

                    if (PlayerDataManager.getData(player).getDeadStatus() == 0) {
                        if(block.getType().equals(Material.AIR)){
                            player.setVelocity(levitationVector);
                        } else {
                            PlayerDataManager.getData(player).setPlayerDeadCine(1);
                        }
                    }


                } else if(ticks <= 164){

                } else {
                    cancel(); // Annuler la tâche après la durée spécifiée
                }
 
                if(ticks == 0){
                    player.setGameMode(GameMode.SPECTATOR);
                    PlayerDataManager.getData(player).setPlayerDeadCine(0);

                    Location loc = player.getLocation();
                    player.teleport(deathLocation);

                    HashMap<String, String> params = new HashMap<>();
                    params.put("%player%", player.getName());

                    if(killer != null){
                        params.put("%killer%", killer.getName());
                    }

                    CommandsHelper.executeAll(Config.getList("deathsystem.commands-death"), params);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 150, 1, false, false));


                }else if(ticks == 50){
                    String title = Config.get("deathsystem.title");
                    player.sendTitle(title, "", 20,160,20);

                } else if (ticks == 66){
                    String title = Config.get("deathsystem.title");
                    if(killer != null) {
                        String subtitle = Config.get("deathsystem.subtitle").replace("%killer%", killer.getName()) ;
                        player.sendTitle(title, subtitle, 0,140,20);
                    }
                } else if(ticks == 100) {
                    PlayerDataManager.getData(player).setPlayerDeadCine(1);

                } else if(ticks == 140){
                    HashMap<String, String> params = new HashMap<>();
                    params.put("%player%", player.getName());
                    if(killer != null){
                        params.put("%killer%", killer.getName());
                    }
                    CommandsHelper.executeAll(Config.getList("deathsystem.commands-respawn"), params);
                } else if(ticks == 160) {
                    player.teleport(Main.getInstance().spawnLoc);
                    PlayerDataManager.getData(player).removePlayerDeadCine();
                    player.setGameMode(GameMode.SURVIVAL);//Remove Player
                }

                ticks = ticks+1;
            }
        }.runTaskTimer(Main.getInstance(), 0, 1); // Exécuter la tâche toutes les 1 tick

        ItemStack phone = CreateItems.itemsCreated.get("phone");
        event.getDrops().removeIf(item -> item.equals(phone));
    }

    @EventHandler
    public void onTarget(EntityTargetLivingEntityEvent event){

        Entity entity = event.getEntity();

        if(entity instanceof PiglinAbstract){
            if(event.getReason().equals(EntityTargetEvent.TargetReason.CLOSEST_PLAYER)){ //Attaque le joueur le plus proche si target NULL
                event.setCancelled(true);
            } else if(event.getReason().equals(EntityTargetEvent.TargetReason.COLLISION)){ //Collision Nouveau calcul du Target
                event.setCancelled(true);
            }
        }
    }

}
