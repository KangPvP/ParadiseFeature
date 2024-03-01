package fr.paradise.feature.listeners.player;

import fr.paradise.feature.Main;
import fr.paradise.feature.data.PlayerDataManager;
import fr.paradise.feature.utils.CommandsHelper;
import fr.paradise.feature.utils.Config;
import fr.paradise.feature.utils.CreateItems;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PlayerJoin implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();

        BossBar barInfo = Bukkit.createBossBar("My Regions", BarColor.YELLOW, BarStyle.SOLID);
        barInfo.setVisible(true);
        barInfo.addPlayer(player);

        PlayerDataManager.setData(event.getPlayer(), barInfo);


        //Telephone emplacement 9
        ItemStack item = CreateItems.itemsCreated.get("phone");
        player.getInventory().setItem(8, item);


        //Bienvenue
        if(!player.hasPlayedBefore()){
            Main.getWelcomer().newPlayerCome(player);
        } else {
            event.setJoinMessage(Config.getColored("welcome.msgjoin").replace("%player%", player.getName()));
        }


        //Système de palier
        int nbOnlinePlayer = Bukkit.getOnlinePlayers().size();
        ArrayList<Integer> palier = Config.getPalierKeys();

        if(palier.contains(nbOnlinePlayer)){
            if(nbOnlinePlayer <= Main.getInstance().lastPalier){
                return;
            }
            List<String> commands = Config.getList("welcome.paliers."+ nbOnlinePlayer +".commands");

            for(Player pls : Bukkit.getOnlinePlayers()){
                HashMap<String, String> param = new HashMap<>();
                param.put("%player%", pls.getName());
                CommandsHelper.executeAll(commands, param);
            }

            Main.getInstance().lastPalier = nbOnlinePlayer;
        }
    }

}
