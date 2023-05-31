package fr.paradise.feature.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class CreateItems {

    public static HashMap<String, ItemStack> itemsCreated = new HashMap<>();

    public static ItemStack itemCreate(final Material material, final String name, final int customModelData, final List<String> lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore((List)lore);
            meta.setCustomModelData(Integer.valueOf(customModelData));
        }
        item.setItemMeta(meta);
        return item;
    }


    public static void itemsSave(){
        ItemStack phone = CreateItems.itemCreate(Config.getMaterial("items.phone.material"),
                Config.getColored("items.phone.name"),
                Config.getInt("items.phone.custom-model-data"),
                Config.getColoredList("items.phone.lore")
        );
        CreateItems.itemsCreated.put("phone", phone);

    }


}
