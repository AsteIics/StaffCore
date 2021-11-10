package me.astelics.staffcore.listeners;

import me.astelics.staffcore.StaffCore;
import me.astelics.staffcore.inventories.FreezeInventory;
import me.astelics.staffcore.managers.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FreezeListener implements Listener {
    StaffCore plugin;

    public FreezeListener(StaffCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (plugin.frozenPlayers.contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (plugin.frozenPlayers.contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity victim = event.getEntity();

        if (victim instanceof Player) {
            Player playerVictim = (Player) victim;
            String victimName = playerVictim.getName();

            if (plugin.frozenPlayers.contains(playerVictim)) {
                event.setCancelled(true);

                if (damager instanceof Player) {
                    Player playerDamager = (Player) damager;

                    if (plugin.frozenPlayers.contains(playerDamager)) {
                        event.setCancelled(true);
                        playerDamager.sendMessage(ChatColor.RED + "You are frozen.");
                    }

                    playerDamager.sendMessage(ChatColor.RED + victimName + " is currently frozen");
                }
            }
        }
    }

    @EventHandler
    public void OnInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (plugin.frozenPlayers.contains(player)) {
            FreezeInventory.CreateFreezeGUI(player);
        }
    }

    @EventHandler
    public void OnFrozenDisconnect(PlayerQuitEvent event) {
        Player frozenPlayer = event.getPlayer();

        if (plugin.frozenPlayers.contains(frozenPlayer)) {
            MessageManager.sendFrozenLogout(frozenPlayer);
        }
    }
}

