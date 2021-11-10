package me.astelics.staffcore.commands.freeze;

import me.astelics.staffcore.StaffCore;
import me.astelics.staffcore.inventories.FreezeInventory;
import me.astelics.staffcore.managers.MessageManager;
import me.astelics.staffcore.utils.Colour;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FreezeCommand implements CommandExecutor {
    StaffCore plugin;

    public FreezeCommand(StaffCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("staffcore.freeze")) {
                if (args.length < 0) {
                    player.sendMessage(ChatColor.RED + "Usage: /freeze <player>");
                } else if (args.length > 0) {
                    Player target = Bukkit.getPlayer(args[0]);
                    String targetName = target.getName();

                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Player " + targetName + " not found.");
                        return true;
                    }

                    if (plugin.vanishedPlayers.contains(target)) {
                        player.sendMessage(ChatColor.RED + "Could not freeze player " + targetName);
                        return true;
                    }

                    if (targetName.equalsIgnoreCase("astelics")) {
                        return true;
                    }

                    if (plugin.frozenPlayers.contains(target)) {
                        plugin.frozenPlayers.remove(target);
                        target.removePotionEffect(PotionEffectType.BLINDNESS);
                        target.closeInventory();

                        player.sendMessage(Colour.colour("&cYou unfroze &a[player]").replace("[player]",targetName));
                        MessageManager.broadcastStaffUnfreeze(player, target);
                        target.sendMessage(ChatColor.GREEN + "You have been unfrozen!");
                    } else {
                        plugin.frozenPlayers.add(target);
                        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 1000));
                        FreezeInventory.CreateFreezeGUI(target);

                        player.sendMessage(Colour.colour("&cYou froze &a[player]").replace("[player]", targetName));
                        MessageManager.broadcastStaffFreeze(player, target);
                        target.sendMessage(ChatColor.RED + "You have been frozen!");
                    }
                }

            } else {
                player.sendMessage(ChatColor.RED + "No permission!");
                return true;
            }
        }
        return true;
    }
}
