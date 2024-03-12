package fr.paradise.feature.listeners.player;

import fr.paradise.feature.Main;
import fr.paradise.feature.utils.CommandsHelper;
import fr.paradise.feature.utils.Config;
import fr.paradise.feature.utils.CreateItems;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.EnderChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Chest System rename
        if (event.getClickedBlock() != null){
            if (event.getClickedBlock().getState() instanceof Chest) {
                Chest chest = (Chest) event.getClickedBlock().getState();

                if(chest.getInventory().getSize() == 54){
                    chest.setCustomName(Config.get("renamechest.largechest"));
                } else {
                    chest.setCustomName(Config.get("renamechest.chest"));
                }
                chest.update();
            }
        }

        if (item.getType() == Material.AIR) {
            return;
        }

        if(player.getGameMode().equals(GameMode.SPECTATOR)){
            event.setCancelled(true);
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("%player%", player.getName());

        ItemStack phone = CreateItems.itemsCreated.get("phone");

        if (item.equals(phone)) {
            CommandsHelper.executeAll(Config.getList("items.phone.commands"), params);
            event.setCancelled(true);
        }


    }
}
