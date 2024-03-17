package fr.paradise.feature.utils.armorstand;

import fr.paradise.feature.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConfigManager {
	private ConfigManager() {}

	private Main plugin = Main.getPlugin(Main.class);

	private static ConfigManager instance = new ConfigManager();

	public static ConfigManager getInstance() {
		return instance;
	}

	// File & File config => Here
	public static FileConfiguration datasystemcfg;
	private File datasystemfile;
	// ......................

	public void setup() {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		datasystemfile = new File(plugin.getDataFolder(), "datasystem.yml");

		if (!datasystemfile.exists()) {
			try {
				datasystemfile.createNewFile();
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Le ficher datasystem.yml a ete cree");
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Le ficher datasystem.yml n'a pas pu etre cree");
			}
		}

		datasystemcfg = YamlConfiguration.loadConfiguration(datasystemfile);

	}

	public FileConfiguration getPlayersData() {
		return datasystemcfg;
	}

	public void savePlayersData() {
		try {
			datasystemcfg.save(datasystemfile);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Le fichier datasystem.yml a ete sauvegarde");
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Le fichier datasystem.yml n'a pas pu etre sauvegarde");
		}

	}

	public void reloadPlayersData() {
		datasystemcfg = YamlConfiguration.loadConfiguration(datasystemfile);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Le ficher datasystem.yml a ete charger");
	}


	public void setString(String path, String value){
		datasystemcfg.set(path, value);
	}
	public String getString(String path){
		if (ConfigManager.datasystemcfg.contains(path)) {
			return ConfigManager.datasystemcfg.getString(path);
		}
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "This value doesn't exist in configuration file :" + path);
		return "";
	}

	public void setInt(String path, Integer value){
		datasystemcfg.set(path, value);
	}

	public Integer getInt(String path){
		if (ConfigManager.datasystemcfg.contains(path)) {
			return ConfigManager.datasystemcfg.getInt(path);
		}
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "This value doesn't exist in configuration file :" + path);
		return 0;
	}

	public void setBoolean(String path, Boolean value){
		datasystemcfg.set(path, value);
	}

	public Boolean getBoolean(String path){
		if (ConfigManager.datasystemcfg.contains(path)) {
			return ConfigManager.datasystemcfg.getBoolean(path);
		}
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "This value doesn't exist in configuration file :" + path);
		return false;
	}

	public void setList(final String path, List<String> value) {
		ConfigManager.datasystemcfg.set(path, value);

	}


	public List<String> getList(String path) {
		if (ConfigManager.datasystemcfg.contains(path)) {
			return (List<String>) ConfigManager.datasystemcfg.getStringList(path);
		}
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "This value doesn't exist in configuration file : " + path);
		return new ArrayList<>();
	}

	public ArrayList<UUID> getPortes() {

		ArrayList<UUID> palier = new ArrayList<>();

		if(ConfigManager.datasystemcfg == null){
			return palier;
		}

		if (ConfigManager.datasystemcfg.contains("porteslist")) {
			ConfigurationSection cs = ConfigManager.datasystemcfg.getConfigurationSection("porteslist");

			for(String s : cs.getKeys(false)) {
				palier.add(UUID.fromString(s));
			}
		}

		return palier;
	}

	public void savePortes(List<PorteAuto> addPorte, List<UUID> removePorte){

		for(PorteAuto porte : addPorte){
			String path = "porteslist." + porte.uuid;

			setString(path+".gate" , porte.gate);
			setString(path+".location" , porte.location);
			setInt(path+".direction" , porte.direction);
			setString(path+".axe" , porte.axe);
			setInt(path+".actionToOpen" , porte.actionToOpen);
			setBoolean(path+".isOpen" , porte.isOpen);
		}

		for(UUID id : removePorte){
			String path = "porteslist." + id;

			datasystemcfg.set(path, null);
		}

	}

	public void saveSpawnPoints(List<UUID> addSpawnPoints, List<UUID> removeSpawnPoints){

		//Save Position SpawnPoints
		for (UUID id : addSpawnPoints) {
			String locStr = ConfigManager.getInstance().locLocToStr(Main.getSpawnMap().spawnpoints.get(id));
			ConfigManager.getInstance().setString("spawnmap.spawnpoints." + id, locStr);
		}

		if (ConfigManager.datasystemcfg.contains("spawnmap.spawnpoints")){
			for(UUID id : removeSpawnPoints){
				String path = "spawnmap.spawnpoints." + id;
				datasystemcfg.set(path, null);
			}
		}
	}

	public String locLocToStr(Location loc){

		String locStr = loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch();
		return locStr;
	}

	public Location locStrToLoc(String locStr){
		String[] locArray = locStr.split(":");

		Location loc = new Location(Bukkit.getWorld(locArray[0]),
				Double.parseDouble(locArray[1]),
				Double.parseDouble(locArray[2]),
				Double.parseDouble(locArray[3]),
				Float.parseFloat(locArray[4]),
				Float.parseFloat(locArray[5]));

		return loc;
	}


	public ArrayList<UUID> getSpawnPoints() {

		ArrayList<UUID> spawnPoints = new ArrayList<>();

		if(ConfigManager.datasystemcfg == null){
			return spawnPoints;
		}

		if (ConfigManager.datasystemcfg.contains("spawnmap.spawnpoints")) {
			ConfigurationSection cs = ConfigManager.datasystemcfg.getConfigurationSection("spawnmap.spawnpoints");

			for(String s : cs.getKeys(false)) {
				spawnPoints.add(UUID.fromString(s));
			}
		}

		return spawnPoints;
	}
}