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

        if (strings.length == 2) {
            Player player = Bukkit.getPlayer(strings[0]);
            String displayRegionName = strings[1];

            if(player == null){
                return false;
            }

            PlayerData playerData = PlayerDataManager.getData(player);

            if(!displayRegionName.equals(playerData.getBarInfoMap().getTitle())){
                playerData.setDataPlayerBar(displayRegionName);
                return true;
            }

        } else {
            System.out.println("Erreur sur la commande EditBarInfoMap");
        }

        return false;
    }
}
