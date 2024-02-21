package fr.paradise.feature.listeners.player;

import fr.paradise.feature.Main;
import fr.paradise.feature.data.PlayerDataManager;
import fr.paradise.feature.utils.Config;
import fr.paradise.feature.utils.CreateItems;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        player.setGameMode(GameMode.SPECTATOR);
        PlayerDataManager.getData(player).setPlayerDeadCine(0);
        Vector levitationVector = new Vector(0, 0.1, 0);

        Location loc = player.getLocation();
        player.teleport( new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 0,90) );

        new BukkitRunnable() {
            int ticks = 0;

            public void run() {
                if (ticks <= 100) {
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

                if(ticks == 50){
                    String title = Config.get("deathsystem.title");
                    String subtitle = Config.get("deathsystem.subtitle").replace("%killer%", player.getName()) ;

                    player.sendTitle(title, subtitle);
                } else if (ticks == 56){
                    player.playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_BREAK, 2, 90);
                } else if(ticks == 100){
                    PlayerDataManager.getData(player).setPlayerDeadCine(1);
                } else if(ticks == 160){
                    player.setGameMode(GameMode.SURVIVAL);
                    player.teleport(Main.getInstance().spawnLoc);
                    PlayerDataManager.getData(player).removePlayerDeadCine(); //Remove Player
                }

                ticks = ticks+2;
            }
        }.runTaskTimer(Main.getInstance(), 0, 2); // Exécuter la tâche toutes les 1 tick

        ItemStack phone = CreateItems.itemsCreated.get("phone");
        event.getDrops().removeIf(item -> item.equals(phone));


    }

}
