package me.astelics.staffcore.inventories;

import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerStore {
    private String playerName;
    private String displayName;
    private UUID uuid;

    private PlayerStore(Player player) {
        this.playerName = player.getName();
        this.displayName = player.getDisplayName();
        this.uuid = player.getUniqueId();
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public static PlayerStore of(Player player) {
        return new PlayerStore(player);
    }
}
