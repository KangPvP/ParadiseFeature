package fr.paradise.feature.utils.armorstand;

import fr.paradise.feature.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class PorteAuto {

    public static HashMap<UUID, PorteAuto> listPortes = new HashMap<>();
    public static HashMap<String, ArrayList<PorteAuto>> groupPortes = new HashMap<String, ArrayList<PorteAuto>>();

    public UUID uuid;
    public String gate;
    public String location;
    public int direction;
    public String axe;
    public int actionToOpen;
    public boolean isOpen;


    public PorteAuto(UUID id, Location location, String gateName){
        this.uuid = id;
        this.gate = gateName;
        this.location = locationString(location);
        this.direction = arroundYaw(location);
        this.axe = getAxe(this.direction);
        this.actionToOpen = getactionToOpen(this.direction);
        this.isOpen = false;

        PorteAuto.listPortes.put(id, this);


        // Crée une HashMap avec les portes link et leurs nom

        if(PorteAuto.groupPortes.containsKey(gateName)){
            ArrayList<PorteAuto> listePorteExiste = PorteAuto.groupPortes.get(gateName);
            listePorteExiste.add(this);

            PorteAuto.groupPortes.put(gateName, listePorteExiste);
        } else {
            ArrayList<PorteAuto> listePorte = new ArrayList<>();
            listePorte.add(this);
            PorteAuto.groupPortes.put(gateName, listePorte);
        }



    }

    public static void loadPortes(){
        ArrayList<UUID> portesSave = ConfigManager.getInstance().getPortes();

        if(ConfigManager.datasystemcfg == null || !ConfigManager.datasystemcfg.contains("porteslist")){
            return;
        }

        for(UUID id : portesSave){
            String path = "porteslist." + id;
            Location loc = PorteAuto.getLocationFile( ConfigManager.getInstance().getString(path + ".location") );
            String gateName = ConfigManager.getInstance().getString(path + ".gate");

            new PorteAuto(id, loc, gateName);
        }

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Les \"Portes Automatique\" ont été chargé");
        new PorteRunnable().runTaskTimer(Main.getInstance(), 20,5);

    }


    public String locationString(Location loc){

        String locStr = loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch();
        return locStr;
    }

    public void setIsOpen(boolean status){
        this.isOpen = status;
    }

    public Location getLocation(){
        String locStr = this.location;
        String[] locArray = locStr.split(":");

        Location loc = new Location(Bukkit.getWorld(locArray[0]),
                Double.parseDouble(locArray[1]),
                Double.parseDouble(locArray[2]),
                Double.parseDouble(locArray[3]),
                Float.parseFloat(locArray[4]),
                Float.parseFloat(locArray[5]));

        return loc;
    }

    public static Location getLocationFile(String locStr){
        String[] locArray = locStr.split(":");

        Location loc = new Location(Bukkit.getWorld(locArray[0]),
                Double.parseDouble(locArray[1]),
                Double.parseDouble(locArray[2]),
                Double.parseDouble(locArray[3]),
                Float.parseFloat(locArray[4]),
                Float.parseFloat(locArray[5]));

        return loc;
    }


    public int getactionToOpen(int yawArround){

        if(yawArround == -90){
            return (-1);
        }
        if(yawArround == 90){
            return 1;
        }
        if(yawArround == 0){
            return (-1);
        }
        if(yawArround == 180){
            return 1;
        }
        return 0;

    }
    public String getAxe(int yawArround){

        if(yawArround == -90 || yawArround == 90){
            return "x";
        }
        if(yawArround == 0 || yawArround == 180){
            return "z";
        }
        return "x";
    }
    public int arroundYaw(Location loc){

        double rotation = (loc.getYaw() - 90.0F) % 360.0F;
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }


        if ((0.0D <= rotation) && (rotation < 45.0D)) {
            return 90; //WEST;
        }
        if ((45.0D <= rotation) && (rotation < 135.D)) {
            return 180; //NORTH
        }
        if ((135.0D <= rotation) && (rotation < 225.0D)) {
            return (-90); //EAST;
        }
        if ((225.0D <= rotation) && (rotation < 315.0D)) {
            return 0; //SOUTH;
        }
        if ((315.0D <= rotation) && (rotation < 360.0D)) {
            return 90; //WEST;
        }
        return 0;
    }

    public static PorteAuto getPorteFromLoc(Location location){
        for (HashMap.Entry<UUID, PorteAuto> entry : listPortes.entrySet()) { //ForEach value in HashMap
            PorteAuto value = entry.getValue();

            Location locValue = value.getLocation();

            if(locValue.getWorld().equals(location.getWorld())){
                if(locValue.getX() == location.getX()){
                    if(locValue.getY() == location.getY()){
                        if(locValue.getZ() == location.getZ()){
                            return value;
                        }
                    }
                }
            }
        }
        return null;
    }


    public static ArrayList<UUID> porteRemove(ArrayList<UUID> portelist){
        ArrayList<UUID> porteRemove = new ArrayList<>();

        for (UUID id : portelist) {
            if(!listPortes.containsKey(id)) {
                porteRemove.add(id);
            }
        }
        return porteRemove;
    }
    public static ArrayList<PorteAuto> porteNoSave(ArrayList<UUID> portelist){

        ArrayList<PorteAuto> porteNoSave = new ArrayList<>();

        for (HashMap.Entry<UUID, PorteAuto> entry : listPortes.entrySet()) {
            UUID id = entry.getKey();
            PorteAuto porte = entry.getValue();

            if(!portelist.contains(id)){
                porteNoSave.add(porte);
            }
        }
        return porteNoSave;
    }

    public PorteAuto getPorteLink(){

        String gateName = this.gate;

        for (HashMap.Entry<UUID, PorteAuto> entry : listPortes.entrySet()) {
            UUID id = entry.getKey();
            PorteAuto porte = entry.getValue();

            if(porte.gate.equals(gateName)){ //If les deux portes on le meme nom de Gate
                if(!id.equals(this.uuid)){ //If la porte get != a la porte
                    return porte;
                }
            }
        }
        return null;
    }

    public Location pointOfDetection(){
        PorteAuto porteLink = this.getPorteLink();
        if (porteLink != null){ // C'est une double porte
            double xMid = (this.getLocation().getX() + porteLink.getLocation().getX()) / 2;
            double yMid = (this.getLocation().getY() + porteLink.getLocation().getY()) / 2;
            double zMid = (this.getLocation().getZ() + porteLink.getLocation().getZ()) / 2;


            if(this.getLocation().getWorld() != porteLink.getLocation().getWorld()){
                System.out.println("Erreur, conflit entre la porte: " + this.getLocation() + " et la porte " + porteLink.getLocation());
                System.out.println("Ces portes on le meme nom mais ne sont pas sur la même map");
            }

            Location locDetection = new Location(this.getLocation().getWorld(), xMid, yMid, zMid);

            return locDetection;
        }
        return this.getLocation();
    }








}
