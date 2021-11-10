package me.astelics.staffcore.commands.modmode;

import me.astelics.staffcore.StaffCore;
import me.astelics.staffcore.managers.InventoryManager;
import me.astelics.staffcore.managers.MessageManager;
import me.astelics.staffcore.utils.Colour;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;

public class ModCommand implements CommandExecutor {
    StaffCore plugin;

    public ModCommand(StaffCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("staffcore.modmode")) {
                if (plugin.modModePlayers.contains(player)) {
                    plugin.modModePlayers.remove(player);
                    player.getInventory().clear();
                    player.getInventory().setArmorContents(new ItemStack[]{new ItemStack(Material.AIR)});
                    try {
                        InventoryManager.restoreInventory(player, plugin);
                    } catch (IOException exception) {
                    }
                    player.sendMessage(Colour.colour("&eYou &cunmodmoded"));
                    MessageManager.sendStaffUnModMode(player);
                } else try {
                    InventoryManager.saveInventory(player, plugin);
                } catch (IOException exception) {
                }
                player.getInventory().clear();
                player.getInventory().setArmorContents(new ItemStack[]{new ItemStack(Material.AIR)});

                //vanish item
                ItemStack vanishItem = new ItemStack(Material.INK_SAC);

                if (plugin.vanishedPlayers.contains(player)) {
                    vanishItem.setDurability((short) 10);

                    ItemMeta vanishItemMeta = vanishItem.getItemMeta();
                    vanishItemMeta.setDisplayName(ChatColor.GRAY + "Unvanish");
                    vanishItem.setItemMeta(vanishItemMeta);
                } else {
                    vanishItem.setDurability((short) 8);

                    ItemMeta vanishItemMeta = vanishItem.getItemMeta();
                    vanishItemMeta.setDisplayName(ChatColor.GREEN + "Vanish");
                    vanishItem.setItemMeta(vanishItemMeta);
                }
                player.getInventory().setItem(0, vanishItem);

                //staff chat item
                ItemStack staffChatItem = new ItemStack(Material.PAPER, 1);
                ItemMeta staffChatItemMeta = staffChatItem.getItemMeta();
                staffChatItemMeta.setDisplayName(ChatColor.BLUE + "Toggle StaffChat");
                staffChatItem.setItemMeta(staffChatItemMeta);
                player.getInventory().setItem(1, staffChatItem);

                //invsee item
                ItemStack invseeItem = new ItemStack(Material.GRASS, 1);
                ItemMeta invseeItemMeta = invseeItem.getItemMeta();
                invseeItemMeta.setDisplayName(ChatColor.YELLOW + "Open player inventory");
                invseeItem.setItemMeta(invseeItemMeta);
                player.getInventory().setItem(2, invseeItem);

                //random teleport
                ItemStack randomTeleportItem = new ItemStack(Material.COMPASS, 1);
                ItemMeta randomTeleportItemMeta = randomTeleportItem.getItemMeta();
                randomTeleportItemMeta.setDisplayName(ChatColor.YELLOW + "Random Teleport");
                randomTeleportItem.setItemMeta(randomTeleportItemMeta);
                player.getInventory().setItem(4, randomTeleportItem);

                //punish item
                ItemStack punishItem = new ItemStack(Material.GOLDEN_AXE, 1);
                ItemMeta punishItemMeta = punishItem.getItemMeta();
                punishItemMeta.setDisplayName(ChatColor.YELLOW + "Punish player");
                punishItem.setItemMeta(punishItemMeta);
                player.getInventory().setItem(6, punishItem);

                //freeze item
                ItemStack freezeItem = new ItemStack(Material.ICE, 1);
                ItemMeta freezeItemMeta = freezeItem.getItemMeta();
                freezeItemMeta.setDisplayName(ChatColor.BLUE + "Freeze player");
                freezeItem.setItemMeta(freezeItemMeta);
                player.getInventory().setItem(7, freezeItem);

                //mod mode item
                ItemStack modModeItem = new ItemStack(Material.INK_SAC);
                modModeItem.setDurability((short) 1);

                ItemMeta modModeItemMeta = modModeItem.getItemMeta();
                modModeItemMeta.setDisplayName(ChatColor.RED + "Toggle Modmode");
                modModeItem.setItemMeta(modModeItemMeta);
                player.getInventory().setItem(8, modModeItem);

                plugin.modModePlayers.add(player);
                MessageManager.sendStaffModMode(player);
                return true;
            } else {
                MessageManager.noPermission(player);
            }
        }
        return true;
    }
}
