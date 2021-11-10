package me.astelics.staffcore.commands.vanish;

import me.astelics.staffcore.StaffCore;
import me.astelics.staffcore.managers.MessageManager;
import me.astelics.staffcore.utils.Colour;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {
    StaffCore plugin;

    public VanishCommand(StaffCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("staffcore.vanish")) {
                if (plugin.vanishedPlayers.contains(player)) {

                    //loops through online players to show player
                    for (Player people : Bukkit.getOnlinePlayers()) {
                        people.showPlayer(plugin, player);
                    }

                    //toggles vanish off if array contains player
                    plugin.vanishedPlayers.remove(player);
                    player.sendMessage(Colour.colour("&eYou &cunvanished"));

                    MessageManager.sendStaffUnvanish(player);

                    return true;
                } else {

                    //loops through online players to hide player
                    for (Player people : Bukkit.getOnlinePlayers()) {
                        if (!people.hasPermission("staffcore.vanish")) {
                            people.hidePlayer(plugin, player);
                        }
                    }

                    //toggled vanish on if player is not in array
                    plugin.vanishedPlayers.add(player);
                    player.sendMessage(Colour.colour("&eYou &avanished"));

                    MessageManager.sendStaffVanish(player);

                    return true;
                }
            } else {
                MessageManager.noPermission(player);
                return true;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "&cThis command is only executable by players");
        }
        return true;
    }
}
