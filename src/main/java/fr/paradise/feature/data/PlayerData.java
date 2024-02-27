package fr.paradise.feature.data;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerData {

    public static HashMap<Player, BossBar> dataPlayerBar = new HashMap<>();
    private Player player;
    private int deadStatus;

    private BossBar barInfoMap;
    public PlayerData(Player player, BossBar barInfo){
        this.player = player;
        this.deadStatus = -1;
        this.barInfoMap = barInfo;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Integer getDeadStatus(){
        return this.deadStatus;
    }

    public void setPlayerDeadCine(int status){
        this.deadStatus = status;
    }

    public void removePlayerDeadCine(){
        this.deadStatus = -1;
    }

    public BossBar getBarInfoMap(){
        return this.barInfoMap;
    }
    public void setDataPlayerBar(String title){
        this.barInfoMap.setTitle(title);
    }




}
