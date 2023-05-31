package fr.paradise.feature.utils;

import fr.paradise.feature.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Config {
    static final File configFile;
    private static void create() {
        Main.getInstance().saveDefaultConfig();
        Main.getInstance().getLogger().info("Configuration file added");
    }
    public static void init() {
        if (!Config.configFile.exists()) {
            create();
        }
    }
    public static void reload() {
        if (Config.configFile.exists()) {
            Main.getInstance().getLogger().info("Configuration file reloaded");
        } else {
            create();
        }
        Main.getInstance().reloadConfig();
    }
    public static String getColored(final String path) {
        if (Main.getInstance().getConfig().contains(path)) {
            return ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString(path));
        }
        Main.getInstance().getLogger().severe("This value doesn't exist in configuration file :" + path);
        return "";
    }

    public static Material getMaterial(final String path) {
        if (Main.getInstance().getConfig().contains(path)) {
            final String messages = Main.getInstance().getConfig().getString(path);
            return Material.valueOf(messages);
        }
        Main.getInstance().getLogger().severe("This value doesn't exist in configuration file :" + path);
        return Material.STONE;
    }

    public static List<String> getColoredList(final String path) {
        if (Main.getInstance().getConfig().contains(path)) {
            final List<String> messages = (List<String>)Main.getInstance().getConfig().getStringList(path);
            messages.replaceAll(msgToColor -> ChatColor.translateAlternateColorCodes('&', msgToColor));
            return messages;
        }
        Main.getInstance().getLogger().severe("This value doesn't exist in configuration file :" + path);
        return null;
    }

    public static int getInt(final String path) {
        if (Main.getInstance().getConfig().contains(path)) {
            return Main.getInstance().getConfig().getInt(path);
        }
        Main.getInstance().getLogger().severe("This value doesn't exist in configuration file :" + path);
        return 0;
    }

    public static double getDouble(final String path) {
        if (Main.getInstance().getConfig().contains(path)) {
            return Main.getInstance().getConfig().getDouble(path);
        }
        Main.getInstance().getLogger().severe("This value doesn't exist in configuration file :" + path);
        return 0.0;
    }

    public static List<String> getList(final String path) {
        if (Main.getInstance().getConfig().contains(path)) {
            return (List<String>)Main.getInstance().getConfig().getStringList(path);
        }
        Main.getInstance().getLogger().severe("This value doesn't exist in configuration file :" + path);
        return null;
    }

    public static Location getLocation(final String path){
        if (Main.getInstance().getConfig().contains(path)) {

            String[] locSplit = Main.getInstance().getConfig().getString(path).split(";");

            String world = locSplit[0];
            double x = Double.parseDouble(locSplit[1]);
            double y = Double.parseDouble(locSplit[2]);
            double z = Double.parseDouble(locSplit[3]);
            float yaw = Float.parseFloat(locSplit[4]);
            float pitch = Float.parseFloat(locSplit[5]);

            return new Location(Bukkit.getWorld(world), x,y,z, yaw, pitch);

        }
        Main.getInstance().getLogger().severe("This value doesn't exist in configuration file :" + path);
        return null;
    }

    public static ArrayList<Integer> getPalierKeys() {
        if (Main.getInstance().getConfig().contains("welcome.paliers")) {
            ConfigurationSection cs = Main.getInstance().getConfig().getConfigurationSection("welcome.paliers");
            ArrayList<Integer> palier = new ArrayList<>();

            for(String s : cs.getKeys(false)) {
                palier.add(Integer.parseInt(s));
            }

            return palier;
        }
        Main.getInstance().getLogger().severe("This value doesn't exist in configuration file :welcome.paliers");
        return null;
    }

    public static List<Object> getCategoriesValues() {

        if (Main.getInstance().getConfig().contains("settings.categories")) {
            final ConfigurationSection configurationSection = Main.getInstance().getConfig().getConfigurationSection("settings.categories");

            return new ArrayList<Object>(configurationSection.getValues(false).values());
        }
        Main.getInstance().getLogger().severe("This value doesn't exist in configuration file :settings.categories");
        return null;
    }

    public static String get(final String path) {
        if (Main.getInstance().getConfig().contains(path)) {
            return Main.getInstance().getConfig().getString(path);
        }

        Main.getInstance().getLogger().severe("This value doesn't exist in configuration file :" + path);
        return null;
    }

    static {
        configFile = new File(Main.getInstance().getDataFolder(), "config.yml");
    }





}
