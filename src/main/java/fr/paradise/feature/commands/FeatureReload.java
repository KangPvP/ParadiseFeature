package fr.paradise.feature.commands;

import fr.paradise.feature.utils.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class FeatureReload implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.hasPermission("dynasty.reload")) {
            Config.reload();
            sender.sendMessage("Vous avez reload la config du plugin feature");
        }

        return false;
    }
}
