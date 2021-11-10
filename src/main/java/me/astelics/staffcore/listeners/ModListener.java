package me.astelics.staffcore.listeners;

import me.astelics.staffcore.StaffCore;
import me.astelics.staffcore.managers.InventoryManager;
import me.astelics.staffcore.managers.MessageManager;
import me.astelics.staffcore.utils.Colour;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ModListener implements Listener {
    StaffCore plugin;

    public ModListener(StaffCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (plugin.modModePlayers.contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (plugin.modModePlayers.contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void OnItemClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (plugin.modModePlayers.contains(player)) {
            ItemStack heldItem = event.getItem();
            String heldItemName = heldItem.getItemMeta().getDisplayName();

            if (heldItemName.equalsIgnoreCase(ChatColor.GRAY + "Unvanish")) {
                event.setCancelled(true);

                if (plugin.vanishedPlayers.contains(player)) {
                    plugin.vanishedPlayers.remove(player);
                    player.sendMessage(Colour.colour("&eYou &cunvanished"));
                    MessageManager.sendStaffUnvanish(player);
                    return;
                } else if (heldItemName.equalsIgnoreCase(ChatColor.GREEN + "Vanish")) {
                    event.setCancelled(true);

                    if (!plugin.vanishedPlayers.contains(player)) {
                        plugin.vanishedPlayers.add(player);
                        player.sendMessage(Colour.colour("&eYou &avanished"));
                        MessageManager.sendStaffVanish(player);
                        return;
                    }
                }
            } else if (heldItemName.equalsIgnoreCase(ChatColor.BLUE + "Toggle StaffChat")) {
                event.setCancelled(true);

                if (plugin.scToggledPlayers.contains(player)) {
                    plugin.scToggledPlayers.remove(player);
                    player.sendMessage(Colour.colour("&b[S] &3Staff chat disabled"));
                    return;
                } else {
                    plugin.scToggledPlayers.add(player);
                    player.sendMessage(Colour.colour("&b[S] &3Staff chat enabled"));
                    return;
                }
            } else if (heldItemName.equalsIgnoreCase(ChatColor.YELLOW + "Random Teleport")) {
                event.setCancelled(true);

                ArrayList<Player> players = new ArrayList<Player>();

                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    if (!plugin.modModePlayers.contains(player1)) {
                        if (plugin.vanishedPlayers.contains(player1)) {
                            continue;
                        }
                        players.add(player1);
                    }
                }

                if (players.isEmpty()) {
                    player.sendMessage(ChatColor.RED + "There are no players to teleport to!");
                    return;
                }

                Player randomPlayer = players.get(new Random().nextInt(players.size()));

                if (plugin.vanishedPlayers.contains(player)) {
                    player.teleport(randomPlayer.getLocation());
                    player.sendMessage(Colour.colour("&3Teleported to &a" + randomPlayer.getName()));
                    return;
                } else {
                    player.sendMessage(ChatColor.RED + "Please vanish before random teleporting.");
                    return;
                }
            } else if (heldItemName.equalsIgnoreCase(ChatColor.RED + "Toggle Modmode")) {
                event.setCancelled(true);

                if (plugin.modModePlayers.contains(player)) {
                    plugin.modModePlayers.remove(player);

                    player.getInventory().clear();
                    player.getInventory().setArmorContents(new ItemStack[]{new ItemStack(Material.AIR)});
                    try {
                        InventoryManager.restoreInventory(player, plugin);
                    } catch (IOException exception) {
                        player.sendMessage(Colour.colour("&eYou &cunmodmoded"));
                        MessageManager.sendStaffUnModMode(player);
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void OnPlayerClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (!plugin.modModePlayers.contains(player)) {
            return;
        }

        if (event.getPlayer().getInventory().getItemInMainHand() == null && event.getPlayer().getInventory().getItemInOffHand() == null) {
            return;
        }

        if (event.getRightClicked() instanceof Player) {
            Player target = (Player) event.getRightClicked();

            ItemStack heldItem = event.getPlayer().getInventory().getItemInMainHand();
            String heldItemName = heldItem.getItemMeta().getDisplayName();

            if (heldItemName.equalsIgnoreCase(ChatColor.YELLOW + "Open player inventory")) {
                event.setCancelled(true);

                player.sendMessage(ChatColor.GREEN + "Opening " + target.getName() + "'s inventory");
                player.openInventory((Inventory) target.getInventory());
                return;
            } else if (heldItemName.equalsIgnoreCase(ChatColor.BLUE + "Freeze player")) {
                event.setCancelled(true);

                if (player.hasPermission("staffcore.freeze")) {
                    if (plugin.frozenPlayers.contains(target)) {
                        plugin.frozenPlayers.remove(target);

                        target.sendMessage(ChatColor.GREEN + "You have been unfrozen!");
                        player.sendMessage(Colour.colour("&cYou unfroze &a[player]").replace("[player]",target.getName()));
                        MessageManager.broadcastStaffUnfreeze(player, target);
                        return;
                    } else {
                        plugin.frozenPlayers.add(target);

                        target.sendMessage(ChatColor.RED + "You have been frozen!");
                        player.sendMessage(Colour.colour("&cYou froze &a[player]").replace("[player]",target.getName()));
                        MessageManager.broadcastStaffFreeze(player, target);
                        return;
                    }
                }
            } else if (heldItemName.equalsIgnoreCase(ChatColor.RED + "Punish player")) {
                event.setCancelled(true);

                if (player.hasPermission("staffcore.punish")) {
                    player.sendMessage(ChatColor.RED + "Coming soon...");
                    return;
                }
            }
        } else {
            return;
        }
    }
}
