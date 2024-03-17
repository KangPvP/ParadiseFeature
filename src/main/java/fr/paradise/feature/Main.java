package fr.paradise.feature;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import fr.paradise.feature.commands.*;
import fr.paradise.feature.data.PlayerDataManager;
import fr.paradise.feature.listeners.player.*;
import fr.paradise.feature.listeners.world.WorldBlockFrom;
import fr.paradise.feature.system.CinematicFixer;

import java.util.ArrayList;
import java.util.UUID;

import fr.paradise.feature.utils.Config;
import fr.paradise.feature.utils.armorstand.ConfigManager;
import fr.paradise.feature.utils.CreateItems;
import fr.paradise.feature.utils.armorstand.PorteAuto;
import fr.paradise.feature.utils.spawnpoints.SpawnMap;
import fr.paradise.feature.utils.welcome.Welcomer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class Main extends JavaPlugin implements Listener {
    private static Main instance;
    private static Welcomer welcomer;

    private static SpawnMap spawnmap;
    private CinematicFixer cinematicFixer;

    public Location spawnLoc;


    public static ArrayList array = new ArrayList();
    public int nbPlayer = 0;
    public int lastPalier = 0;


    public void onEnable() {
        instance = this;

        Config.init();

        ConfigManager.getInstance().setup();
        ConfigManager.getInstance().savePlayersData();
        ConfigManager.getInstance().reloadPlayersData();

        CreateItems.itemsSave();
        welcomer = new Welcomer();
        welcomer.setupSystemCmd();

        spawnmap = new SpawnMap();

        //LoadConfigFile
        Bukkit.getPluginCommand("chatcancel").setExecutor(new ChatCancel());
        Bukkit.getPluginCommand("porte").setExecutor(new Porte());
        Bukkit.getPluginCommand("clearchat").setExecutor(new ClearChat());
        Bukkit.getPluginCommand("spawnmap").setExecutor(new fr.paradise.feature.commands.SpawnMap());
        Bukkit.getPluginCommand("featurereload").setExecutor(new FeatureReload());
        Bukkit.getPluginCommand("testspawn").setExecutor(new TestSpawn());
        Bukkit.getPluginCommand("editbarinfomap").setExecutor(new EditBarInfoMap());


        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerClickInv(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDrop(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerSwapHand(), this);
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerSendMsg(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerBreak(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerPlace(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerMove(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteractAtEntity(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteractEntity(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerCommands(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new WorldBlockFrom(), this);

        BukkitScheduler scheduler = this.getServer().getScheduler();
        scheduler.runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                onLoad();
                spawnLoc = Config.getLocation("welcome.location.spawn");
            }
        }, 100L);
    }

    public void onLoad(){
        //this.cinematicFixer = new CinematicFixer(this);
        PorteAuto.loadPortes();
        //Load zoneTp

        ArrayList<String> regionsZoneArray = (ArrayList<String>) ConfigManager.getInstance().getList("spawnmap.zonetp");
        Main.getSpawnMap().regionsZoneTp = regionsZoneArray;
        //Load spawnPoints
        ArrayList<UUID> spawnPoints = ConfigManager.getInstance().getSpawnPoints();
        for (UUID id : spawnPoints){
            String strLoc = ConfigManager.getInstance().getString("spawnmap.spawnpoints."+id);
            Location loc = ConfigManager.getInstance().locStrToLoc(strLoc);
            Main.getSpawnMap().spawnpoints.put(id, loc);
        }
    }

    public void onDisable() {


        //Save bug spectator Dead
        for(Player player : Bukkit.getOnlinePlayers()){
            int status = PlayerDataManager.getData(player).getDeadStatus();
            if(status == 0 || status == 1){
                player.teleport(Main.getInstance().spawnLoc);
                player.setGameMode(GameMode.SURVIVAL);
                PlayerDataManager.getData(player).removePlayerDeadCine();
            }
        }


        //Save Position Portes
        ArrayList<PorteAuto> portesNoSave = PorteAuto.porteNoSave( ConfigManager.getInstance().getPortes() );
        ArrayList<UUID> porteRemove = PorteAuto.porteRemove( ConfigManager.getInstance().getPortes() );
        ConfigManager.getInstance().savePortes(portesNoSave, porteRemove);

        //Save regionZoneTp
        ConfigManager.getInstance().setList("spawnmap.zonetp", Main.getSpawnMap().regionsZoneTp);

        //Save Position SpawnPoints
        ArrayList<UUID> spawnPointsNoSave = Main.getSpawnMap().spawnPointNoSave( ConfigManager.getInstance().getSpawnPoints() );
        ArrayList<UUID> spawnPointsRemove = Main.getSpawnMap().spawnpointsRemove( ConfigManager.getInstance().getSpawnPoints() );
        ConfigManager.getInstance().saveSpawnPoints(spawnPointsNoSave, spawnPointsRemove);

        ConfigManager.getInstance().savePlayersData();

        instance = null;
    }

    public static Main getInstance() {
        return instance;
    }
    public static Welcomer getWelcomer() { return welcomer; }

    public static SpawnMap getSpawnMap() { return spawnmap; }

    public CinematicFixer getCinematicFixer() {
        return this.cinematicFixer;
    }

    public ProtocolManager getProtocol() {
        return ProtocolLibrary.getProtocolManager();
    }


}
