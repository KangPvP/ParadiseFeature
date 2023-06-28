package fr.paradise.feature.utils.welcome;


import fr.paradise.feature.utils.Config;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Welcomer {



    public Welcomer() {

    }

    public double currentChance = 0.0;
    public int lastPalier = 0;
    public int welcomeDelay = Config.getInt("welcome.system.delay");
    public ArrayList<Object> newPlayer = new ArrayList<>();
    public HashMap<Player, ArrayList<Player>> playersPolit = new HashMap<>();
    public HashMap<Integer, SystemCmd> systemCmdList = new HashMap<>();

    public void newPlayerCome(Player player){
        this.newPlayer.clear();
        this.newPlayer.add(player);
        this.newPlayer.add(System.currentTimeMillis());

        this.playersPolit.put(player, new ArrayList<Player>());

        System.out.println(this.newPlayer);
        System.out.println(this.newPlayer.get(0));
        System.out.println(this.newPlayer.get(1));

        System.out.println(this.playersPolit);
    }

    public boolean newPlayerValide(){

        if(this.newPlayer.get(0) == null){
            return false;
        }

        long time = (long) this.newPlayer.get(1);
        if(time + welcomeDelay * 1000L < System.currentTimeMillis()){
            return false;
        }
        return true;
    }

    public boolean messagesValide(String msg){
        List<String> messages = Config.getList("welcome.system.messages");

        for(String message : messages){
            if(message.equalsIgnoreCase(msg)){
                return true;
            }
        }
        return false;
    }

    public void setupSystemCmd(){
        int nbCommands = Config.getInt("welcome.system.rewards.nb");

        for(int i=0; i < nbCommands; i++ ){
            new SystemCmd(i);
        }
    }

    public List<String> giveReward(){

        int nbRewards = Config.getInt("welcome.system.rewards.nb");


        double min = 0.0;  // Set To Your Desired Min Value
        double max = 100.0; // Set To Your Desired Max Value
        double randomDouble = (Math.random() * ((max - min) + 1)) + min;   // This Will Create A Random Number Inbetween Your Min And Max.

        for(int i=0; i < nbRewards; i++ ){
            SystemCmd reward = this.systemCmdList.get(i);

            double minValue = reward.getMinValue();
            double maxValue = reward.getMaxValue();

            if(randomDouble >= minValue && randomDouble <= maxValue){
                return reward.getCommand();
            }

        }

        return null;
    }

}
