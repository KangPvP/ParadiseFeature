package fr.paradise.feature.commands;

import fr.paradise.feature.utils.armorstand.PorteAuto;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Porte implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {



        Player player = (Player) sender;

        Location location = player.getLocation().getBlock().getLocation();
        location.add(0.5, 0.0, 0.5);
        location.setYaw(player.getLocation().getYaw());

        if(args.length == 0){ //message Help Utilisation
            player.sendMessage(ChatColor.LIGHT_PURPLE+  "|| = > Porte Commands :");
            player.sendMessage("/porte help");
            player.sendMessage("/porte remove");
            player.sendMessage("/porte place [gatename]");

            return false;

        }else if (args.length == 1){
            //==============================================================\\
            //                    CMD /porte help
            if(args[0].equalsIgnoreCase("help")){ //CMD /porte help
                player.sendMessage(ChatColor.LIGHT_PURPLE+  "|| = > Porte Commands :");
                player.sendMessage("/porte help");
                player.sendMessage("/porte remove");
                player.sendMessage("/porte place [gatename]");

            //==============================================================\\
            //                    CMD /porte remove
            } else if(args[0].equalsIgnoreCase("remove")){
                PorteAuto porteSelect = PorteAuto.getPorteFromLoc(location);
                if (porteSelect == null) {
                    player.sendMessage("Placé vous a l'endroit d'une porte");
                    return false;
                }
                UUID porteId = porteSelect.uuid;
                PorteAuto.listPortes.remove(porteId);

                player.sendMessage("Vous avez supprimé cette porte");

            //==============================================================\\
            //                    CMD /porte info
            }

        } else if (args.length == 2){
            //==============================================================\\
            //                    CMD /porte place
            if(args[0].equalsIgnoreCase("place")){ //CMD /porte place
                if( namePorteExist(PorteAuto.listPortes, args[1]) ){
                    player.sendMessage("Ce nom de gate est déja utilisé");
                    return false;
                }
                PorteAuto porte = PorteAuto.getPorteFromLoc(location);

                if(porte != null){
                    player.sendMessage("Il y a déja une porte a cette endroit");
                    return false;
                }

                ArmorStand stand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                stand.setVisible(true);
                stand.setGravity(true);
                stand.setGlowing(true);
                stand.setInvulnerable(true);

                new PorteAuto(stand.getUniqueId(), location, args[1]);

                player.sendMessage("Vous avez ajouté une porte");
                return true;
            }

        }

        return false;
    }



        public boolean namePorteExist(HashMap<UUID, PorteAuto> listPortes, String gateName){
            int nbOccurence = 0;

            for (HashMap.Entry<UUID, PorteAuto> entry : listPortes.entrySet()) {
                UUID id = entry.getKey();
                PorteAuto porte = entry.getValue();

                if((porte.gate).equalsIgnoreCase(gateName)){
                    nbOccurence = nbOccurence + 1;
                }

                if(nbOccurence == 2){
                    return true;
                }
            }
            return false;
        }


    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            List<String> action = new ArrayList<>();
            action.add("help");
            action.add("place");
            action.add("remove");
            return action;

        }

        return null;
    }
}
