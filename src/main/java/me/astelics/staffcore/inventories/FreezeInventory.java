package me.astelics.staffcore.inventories;

import me.astelics.staffcore.utils.Colour;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class FreezeInventory {
    public static void CreateFreezeGUI(Player player) {
        //create inventory
        Inventory inventory = Bukkit.createInventory(player, 9);

        //fill inventory
        ItemStack inventoryFiller = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta inventoryFillerMeta = inventoryFiller.getItemMeta();
        inventoryFillerMeta.setDisplayName("");

        //main item
        ArrayList<String> mainItemText = new ArrayList<>();
        ItemStack mainItem = new ItemStack(Material.BARRIER, 1);
        ItemMeta mainItemMeta = mainItem.getItemMeta();

        //main item text
        mainItemMeta.setDisplayName(Colour.colour("&4&lYOU HAVE BEEN FROZEN!"));
        mainItemText.add(Colour.colour(Colour.colour("&cIf you log out you will be banned.")));
        mainItemText.add(Colour.colour("&cPlease join our discord within 3 minutes."));
        mainItemText.add(Colour.colour("&cINSERT DISCORD LINK"));
        mainItemMeta.setLore(mainItemText);

        //set meta
        mainItem.setItemMeta(mainItemMeta);

        //setup inventory
        inventory.setItem(0, inventoryFiller);
        inventory.setItem(1, inventoryFiller);
        inventory.setItem(2, inventoryFiller);
        inventory.setItem(3, inventoryFiller);
        inventory.setItem(4, mainItem);
        inventory.setItem(5, inventoryFiller);
        inventory.setItem(6, inventoryFiller);
        inventory.setItem(7, inventoryFiller);
        inventory.setItem(8, inventoryFiller);

        //open inventory
        player.openInventory(inventory);
    }
}
