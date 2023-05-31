package fr.paradise.feature.data;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerData {

    private Player player;
    private int deadStatus;
    public PlayerData(Player player){
        this.player = player;
        this.deadStatus = -1;
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



}
