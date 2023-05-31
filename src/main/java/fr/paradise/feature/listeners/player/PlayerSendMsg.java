package fr.paradise.feature.listeners.player;

import fr.paradise.feature.Main;
import fr.paradise.feature.utils.CommandsHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerSendMsg implements Listener {

    @EventHandler
    public void onMsg(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();

        if(Main.getWelcomer().messagesValide(message)){

            if(Main.getWelcomer().newPlayerValide()){ //get if time over
                Player newPlayer = (Player) Main.getWelcomer().newPlayer.get(0); //getNewPlayer

                HashMap<Player, ArrayList<Player>> playersPolit = Main.getWelcomer().playersPolit;

                ArrayList<Player> listPlayersPolit = playersPolit.get(newPlayer);  //get listPlayersPolit

                if(listPlayersPolit.contains(player)){
                    player.sendMessage("Vous avez déja shouaité la bienvenue a ce joueurs");
                    return;
                }

                List<String> commands = Main.getWelcomer().giveReward(); //renvoie les commands relative a la récompence tiré
                System.out.println(commands);

                if(commands == null){
                    System.out.println("Erreur récompense introuvable, erreur Config %");
                    return;
                }

                HashMap<String, String> params = new HashMap<>();
                params.put("%player%", player.getName());

                Bukkit.getServer().getScheduler().runTask(Main.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        CommandsHelper.executeAll(commands, params);
                    }
                });

                listPlayersPolit.add(player);



            } else {
                player.sendMessage("Il n'y a pas de nouveau a qui shouhaiter la bienvenue");
            }

        }
    }

}
