package me.astelics.staffcore.listeners;

import me.astelics.staffcore.StaffCore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class VanishListener implements Listener {
    StaffCore plugin;

    public VanishListener(StaffCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //hide player on join
        for (int i = 0; i < plugin.vanishedPlayers.size(); i++) {
            player.hidePlayer(plugin, plugin.vanishedPlayers.get(i));
        }
    }

    @EventHandler
    public void OnAttack(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity victim = event.getEntity();

        //when damaged if player is vanished cancel event
        if (victim instanceof Player) {
            Player player = (Player) victim;
            if (plugin.vanishedPlayers.contains(player)) {
                event.setCancelled(true);
            }
        }

        //when attacking if player is vanished cancel event
        if (damager instanceof Player) {
            Player player = (Player) damager;
            if (plugin.vanishedPlayers.contains(player)) {
                event.setCancelled(true);

                player.sendMessage(ChatColor.RED + "You cannot hit entities whilst vanished!");
            }
        }
    }

    @EventHandler
    public void OnBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        //when breaking a block if player is vanished cancel event
        if (plugin.vanishedPlayers.contains(player)) {
            event.setCancelled(true);

            player.sendMessage(ChatColor.RED + "You cannot break blocks whilst vanished!");
        }
    }

    @EventHandler
    public void OnBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        //when placing a block if player is vanished cancel event
        if (plugin.vanishedPlayers.contains(player)) {
            event.setCancelled(true);

            player.sendMessage(ChatColor.RED + "You cannot place blocks whilst vanished!");
        }
    }

    @EventHandler
    public void OnItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (plugin.modModePlayers.contains(player)) {
            event.setCancelled(true);
        }
    }
}
