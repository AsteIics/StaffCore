package me.astelics.staffcore.managers;

import me.astelics.staffcore.utils.Colour;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageManager {
    public static void sendStaffChat(CommandSender sender, String message) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("staffcore.staffchat")) {
                players.sendMessage(Colour.colour("&b[S] &3&o[[server]] &b[player]: [message]").replace("[server]", Bukkit.getServer().getName()).replace("[player]", sender.getName()).replace("[message]", message));
            }
        }
    }

    public static void sendStaffChat(Player player, String message) {
        sendStaffChat((CommandSender)player, message);
    }

    public static void sendStaffVanish(Player player) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("staffcore.alerts")) {
                players.sendMessage(Colour.colour("&7&o[[player]: &avanished&7&o]").replace("[player]", player.getName()));
            }
        }
    }

    public static void sendStaffUnvanish(Player player) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("staffcore.alerts")) {
                players.sendMessage(Colour.colour("&7&o[[player]: &cunvanished&7&o]").replace("[player]", player.getName()));
            }
        }
    }

    public static void sendStaffModMode(Player player) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("staffcore.alerts")) {
                players.sendMessage(Colour.colour("&7&o[[player]: &amodmoded&7&o]").replace("[player]", player.getName()));
            }
        }
    }

    public static void sendStaffUnModMode(Player player) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("staffcore.alerts")) {
                players.sendMessage(Colour.colour("&7&o[[player]: &cunmodmoded&7&o]").replace("[player]", player.getName()));
            }
        }
    }

    public static void sendStaffJoin(Player player) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("staffcore.alerts")) {
                players.sendMessage(Colour.colour("&b[S] [player] &3connected to &b&o[server]").replace("[player]", player.getName()).replace("[server]", Bukkit.getServer().getName()));
            }
        }
    }

    public static void sendStaffDisconnect(Player player) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("staffcore.alerts")) {
                players.sendMessage(Colour.colour("&b[S] &5&o[player] &3disconnected from &b&o[server]").replace("[player]", player.getName()).replace("[server]", Bukkit.getServer().getName()));
            }
        }
    }

    public static void sendFrozenLogout(Player player) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("staffcore.alerts")) {
                players.sendMessage(Colour.colour("&b[S] &a[player] &3logged out while frozen").replace("[player]", player.getName()));
            }
        }
    }

    public static void broadcastStaffFreeze(Player player, Player frozenPlayer) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("staffcore.alerts")) {
                players.sendMessage(Colour.colour("&b[S] &5&o[player] &3froze &a[frozenplayer]").replace("[player]", player.getName()).replace("[frozenplayer]", frozenPlayer.getName()));
            }
        }
    }

    public static void broadcastStaffUnfreeze(Player player, Player frozenPlayer) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("staffcore.alerts")) {
                players.sendMessage(Colour.colour("&b[S] &5&o[player] &3unfroze &a[frozenplayer]").replace("[player]", player.getName()).replace("[frozenplayer]" + "", frozenPlayer.getName()));
            }
        }
    }

    public static void sendPlayerDisguise(Player player, String playerName) {
        player.sendMessage(Colour.colour("&aYou disguised as " + playerName));
    }

    public static void sendPlayerUndisguise(Player player) {
        player.sendMessage(Colour.colour("&cYou undisguised"));
    }

    public static void noPermission(Player player) {
        player.sendMessage(ChatColor.RED + "No permission!");
    }
}
