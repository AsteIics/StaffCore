package me.astelics.staffcore.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class InventoryManager {
    public static void saveInventory(Player player, JavaPlugin plugin) throws IOException {
        File file = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "inventories", "inventory_" + player.getName() + ".yml");
        FileConfiguration config = (FileConfiguration)YamlConfiguration.loadConfiguration(file);

        config.set("inventory.armor", (Object)player.getInventory().getArmorContents());
        config.set("inventory.content", (Object)player.getInventory().getContents());
        config.save(file);
    }

    public static void restoreInventory(Player player, JavaPlugin plugin) throws IOException {
        File file = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "inventories", "inventory_" + plugin.getName() + ".yml");
        FileConfiguration config = (FileConfiguration)YamlConfiguration.loadConfiguration(file);

        ItemStack[] content = (ItemStack[]) ((List)config.get("inventory.armor")).toArray(new ItemStack[0]);
        player.getInventory().setArmorContents(content);
        content = (ItemStack[]) ((List)config.get("inventory.content")).toArray(new ItemStack[0]);
        player.getInventory().setContents(content);
    }
}
