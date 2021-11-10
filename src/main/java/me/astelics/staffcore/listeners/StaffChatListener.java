package me.astelics.staffcore.listeners;

import me.astelics.staffcore.StaffCore;
import me.astelics.staffcore.managers.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class StaffChatListener implements Listener {
    StaffCore plugin;

    public StaffChatListener(StaffCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnChat(AsyncPlayerChatEvent event) {
        //player variables
        Player player = event.getPlayer();
        String name = player.getName();
        String message = event.getMessage();

        //send message
        if (plugin.scToggledPlayers.contains(player)) {
            //cancel normal chat event
            event.setCancelled(true);

            MessageManager.sendStaffChat(player, message);
        }
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //let staff know player has joined
        MessageManager.sendStaffJoin(player);
    }

    @EventHandler
    public void OnQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        //let staff know player has left
        MessageManager.sendStaffDisconnect(player);
    }
}
