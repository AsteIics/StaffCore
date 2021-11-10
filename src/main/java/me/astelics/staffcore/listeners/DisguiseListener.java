package me.astelics.staffcore.listeners;

import lombok.RequiredArgsConstructor;
import me.astelics.staffcore.managers.DisguiseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class DisguiseListener implements Listener {
    private final DisguiseManager disguiseManager;

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        disguiseManager.deleteDisguise(event.getPlayer());
    }
}
