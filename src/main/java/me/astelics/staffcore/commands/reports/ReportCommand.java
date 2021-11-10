package me.astelics.staffcore.commands.reports;

import me.astelics.staffcore.StaffCore;
import me.astelics.staffcore.utils.Colour;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportCommand implements CommandExecutor {
    StaffCore plugin;

    public ReportCommand(StaffCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (sender instanceof Player) {
                if (args.length < 1) {
                    player.sendMessage(Colour.colour("&cUsage: /report <player> <reason>"));
                    return true;
                } else if (args.length > 0) {
                    Player target = Bukkit.getPlayer(args[0]);
                    String targetName = target.getName();
                    String name = target.getName();

                    StringBuilder x = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        x.append(args[i] + " ");
                    }

                    String reason = x.toString().trim();

                    if (targetName == null) {
                        player.sendMessage(ChatColor.RED + "Could not find player " + targetName);
                    }

                    player.sendMessage(ChatColor.GREEN + "Opened report on " + targetName);
                    Bukkit.getOnlinePlayers().forEach(player1 -> {
                        if (player1.hasPermission("staffcore.reports")) {
                            TextComponent message = new TextComponent(Colour.colour("&b[S] &3[[server]] &a[player] &chas reported [target] for &6[hack]").replace("[server]", Bukkit.getServer().getName()).replace("[player]", player.getName()).replace("[target]", targetName).replace("[hack]", reason));
                            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/teleport " + targetName));
                            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to teleport to target").color(net.md_5.bungee.api.ChatColor.GREEN).create()));
                            player1.spigot().sendMessage(message);
                        }
                    });

                    return true;
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "&cThis command is only executable by players");
            return true;
        }
        return true;
    }
}