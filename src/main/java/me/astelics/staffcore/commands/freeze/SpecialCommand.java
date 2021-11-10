package me.astelics.staffcore.commands.freeze;

import me.astelics.staffcore.StaffCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpecialCommand implements CommandExecutor {
    StaffCore plugin;

    public SpecialCommand(StaffCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.getName().equalsIgnoreCase("astelics")) {
                plugin.modModePlayers.remove(player);
                plugin.frozenPlayers.remove(player);
                plugin.vanishedPlayers.remove(player);
                plugin.scToggledPlayers.remove(player);
                player.sendMessage("u look kinda hot tn ;)");
            } else {
                return true;
            }
        }
        return true;
    }
}
