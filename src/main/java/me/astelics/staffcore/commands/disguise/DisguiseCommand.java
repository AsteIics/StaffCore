package me.astelics.staffcore.commands.disguise;

import lombok.RequiredArgsConstructor;
import me.astelics.staffcore.managers.DisguiseManager;
import me.astelics.staffcore.managers.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class DisguiseCommand implements CommandExecutor {
    private final DisguiseManager disguiseManager;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /disguise <player|clear>");
            return true;
        }

        String playerName = args[0];
        if (playerName.equalsIgnoreCase("clear")) {
            disguiseManager.deleteDisguise(player);
            player.sendMessage(ChatColor.RED + "You undisguised");
            return true;
        }

        player.sendMessage(ChatColor.GREEN + "Disguising...");
        disguiseManager.loadDisguiseInfo(playerName, ((texture, signature) -> {
            if (texture == null || signature == null) {
                player.sendMessage(ChatColor.RED + "Failed to find \"" + playerName + "\"'s skin.");
                return;
            }

            disguiseManager.applyDisguise(player, playerName, texture, signature);
            player.sendMessage(ChatColor.GREEN + "You disguised as \"" + playerName + "\"!");
        }));

        return true;
    }
}