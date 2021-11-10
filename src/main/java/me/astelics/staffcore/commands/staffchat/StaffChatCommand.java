package me.astelics.staffcore.commands.staffchat;

import me.astelics.staffcore.StaffCore;
import me.astelics.staffcore.managers.MessageManager;
import me.astelics.staffcore.utils.Colour;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {
    StaffCore plugin;

    public StaffChatCommand(StaffCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("staffcore.staffchat")) {
                if (args.length > 0) {

                    String message = String.join(" ", (CharSequence[])args);

                    //check online players if they can see chat
                    MessageManager.sendStaffChat(player, message);
                return true;

                } else if (args.length < 1) {
                    if (plugin.scToggledPlayers.contains(player)) {
                        //toggles staffchat off if array contains player
                        plugin.scToggledPlayers.remove(player);
                        player.sendMessage(Colour.colour("&b[S] &3Staff chat disabled"));
                        return true;

                    } else {
                        //toggles staffchat on if array contains player
                        plugin.scToggledPlayers.add(player);
                        player.sendMessage(Colour.colour("&b[S] &3Staff chat enabled"));
                        return true;

                    }
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
