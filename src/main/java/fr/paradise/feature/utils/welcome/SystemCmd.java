package fr.paradise.feature.utils.welcome;

import fr.paradise.feature.Main;
import fr.paradise.feature.utils.Config;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class SystemCmd {


    public int id;
    public double chance;
    public double minValue;
    public double maxValue;
    public List<String> command;

    public SystemCmd(int id){

        String startPath = "welcome.system.rewards." + id;

        this.id = id;
        this.chance = Config.getDouble(startPath + ".chance");

        this.minValue = Main.getWelcomer().currentChance;
        this.maxValue = Main.getWelcomer().currentChance + chance;

        this.command = Config.getList(startPath + ".commands");

        Main.getWelcomer().currentChance = Main.getWelcomer().currentChance + chance;

        Main.getWelcomer().systemCmdList.put(id, this);
    }

    public double getMinValue(){
        return this.minValue;
    }
    public double getMaxValue(){
        return this.maxValue;
    }
    public List<String> getCommand() { return this.command; }



    // 5%   20%   75%
    //0-5 5-25 25-100

}
