package fr.paradise.feature.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TestSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            Creature creature = (Creature) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.PIGLIN);
            Objects.requireNonNull(creature.getEquipment()).setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        }


        return false;
    }
}
