package fr.paradise.feature.commands;


import fr.paradise.feature.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatCancel implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!sender.hasPermission("robot.admin")) {
            sender.sendMessage("&cVous n'avez pas la permission.");
            return false;
        }
        if (strings.length == 0) {
            sender.sendMessage("&cIl manque des arguments: /chatcancel <player>");
            return false;
        }
        Player player = Bukkit.getPlayer(strings[0]);
        if (player == null || !player.isOnline()) {
            sender.sendMessage("&cLe joueur n'existe pas ou n'est pas connect");
            return false;
        }
        boolean result = Main.getInstance().getCinematicFixer().toggleCanceling(player);
        String message = result ? ("&aLe joueur " + player.getName() + " voit les messages") : ("&cLe joueur " + player.getName() + " ne voit plus les messages.");
        sender.sendMessage(message);
        return true;
    }
}
