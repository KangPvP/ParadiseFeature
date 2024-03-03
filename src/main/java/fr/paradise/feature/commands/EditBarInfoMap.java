package fr.paradise.feature.commands;

import fr.paradise.feature.data.PlayerData;
import fr.paradise.feature.data.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EditBarInfoMap implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (strings.length >= 2) {
            Player player = Bukkit.getPlayer(strings[0]);
            StringBuilder displayRegionName = new StringBuilder();

            for(int i=1; i < strings.length;i++){
                if(i+1 == strings.length){
                    displayRegionName.append(strings[i]);
                }else{
                    displayRegionName.append(strings[i]).append(" ");
                }
            }

            if(player == null){
                return false;
            }

            PlayerData playerData = PlayerDataManager.getData(player);
            if(playerData != null){
                playerData.setDataPlayerBar(displayRegionName.toString());
            }

        } else {
            System.out.println("Erreur sur la commande EditBarInfoMap");
        }

        return false;
    }
}
