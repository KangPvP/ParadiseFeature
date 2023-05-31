// 
// Decompiled by Procyon v0.5.36
// 

package fr.paradise.feature.utils;

import fr.paradise.feature.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandsHelper
{
    public static void executeAll(final List<String> commands, final HashMap<String, String> parameters) {
        final ConsoleCommandSender console = Main.getInstance().getServer().getConsoleSender();
        commands.forEach(cmd -> Main.getInstance().getServer().dispatchCommand((CommandSender)console, replaceAllParameters(cmd, parameters)));
    }
    
    private static String replaceAllParameters(String command, final HashMap<String, String> parameters) {
        for (final Map.Entry<String, String> entry : parameters.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();
            command = command.replace(key, value);
        }
        return command;
    }
}
