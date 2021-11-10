package me.astelics.staffcore.commands.invsee;

import me.astelics.staffcore.managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InvseeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("staffcore.invsee")) {
                if (args.length < 1) {
                    player.sendMessage(ChatColor.RED + "Usage: /invsee <player>");
                } else {
                    Player target = Bukkit.getPlayer(args[0]);

                    player.openInventory((Inventory) target.getInventory());
                    player.sendMessage(ChatColor.GREEN + "Opening " + target.getName() + "'s inventory");
                }
            } else {
                MessageManager.noPermission(player);
            }
        }
        return true;
    }
}
