package fr.paradise.feature.data;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerDataManager {

    private static HashMap<Player, PlayerData> dataMap = new HashMap<>(); //Status: 0 = Player dead in Levitation, 1 = Player dead in Freeze

    public static void setData(Player player){
        dataMap.put(player, new PlayerData(player));
    }

    public static PlayerData getData(Player player){
        return dataMap.getOrDefault(player, null);
    }



}
