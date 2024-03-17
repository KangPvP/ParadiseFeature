package fr.paradise.feature.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            PiglinAbstract creature = (PiglinAbstract) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.PIGLIN);
            Objects.requireNonNull(creature.getEquipment()).setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            creature.setImmuneToZombification(true);
            creature.setAdult();
            creature.setRemoveWhenFarAway(false);
            creature.getEquipment().setItemInMainHand(new ItemStack(Material.LEATHER_HORSE_ARMOR));
            creature.setTarget((LivingEntity) player);

            if(strings.length > 2){
                for(Entity entity : player.getNearbyEntities(50,50,50)){
                    if(entity instanceof PiglinAbstract){
                        ((PiglinAbstract) entity).setTarget(player);
                    }
                }
            }
        }



        return false;
    }
}
