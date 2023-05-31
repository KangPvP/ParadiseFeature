package fr.paradise.feature.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearChat implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(args.length == 1){
            Player target = Bukkit.getPlayer(args[0]);
            if(target == null){
                return false;
            }
            if(!target.isOnline()){
                return false;
            }
            for (int i = 0; i < 100; i++) {
                target.sendMessage(" ");
            }
        } else {
            System.out.println("clearchat [player]");
        }

        return false;
    }


}
