package me.astelics.staffcore;

import me.astelics.staffcore.commands.disguise.DisguiseCommand;
import me.astelics.staffcore.commands.freeze.FreezeCommand;
import me.astelics.staffcore.commands.freeze.SpecialCommand;
import me.astelics.staffcore.commands.invsee.InvseeCommand;
import me.astelics.staffcore.commands.modmode.ModCommand;
import me.astelics.staffcore.commands.reports.ReportCommand;
import me.astelics.staffcore.commands.staffchat.StaffChatCommand;
import me.astelics.staffcore.commands.vanish.VanishCommand;
import me.astelics.staffcore.listeners.*;
import me.astelics.staffcore.managers.DisguiseManager;
import me.astelics.staffcore.utils.Colour;
import me.astelics.staffcore.utils.HTTPUtility;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class StaffCore extends JavaPlugin {

    public ArrayList<Player> scToggledPlayers = new ArrayList<>();
    public ArrayList<Player> vanishedPlayers = new ArrayList<>();
    public ArrayList<Player> frozenPlayers = new ArrayList<>();
    public ArrayList<Player> modModePlayers = new ArrayList<>();

    private DisguiseManager disguiseManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println(Colour.colour("&aPlugin enabled!"));

        HTTPUtility httpUtility = new HTTPUtility();
        this.disguiseManager = new DisguiseManager(this, httpUtility);

        //staff chat
        getCommand("staffchat").setExecutor(new StaffChatCommand(this));
        getServer().getPluginManager().registerEvents(new StaffChatListener(this), this);

        //vanish
        getCommand("vanish").setExecutor(new VanishCommand(this));
        getServer().getPluginManager().registerEvents(new VanishListener(this), this);

        //freeze
        getCommand("freeze").setExecutor(new FreezeCommand(this));
        getServer().getPluginManager().registerEvents(new FreezeListener(this), this);

        //report
        getCommand("report").setExecutor(new ReportCommand(this));

        //mod mode
        getCommand("mod").setExecutor(new ModCommand(this));
        getServer().getPluginManager().registerEvents(new ModListener(this), this);

        //disguise
        getCommand("disguise").setExecutor(new DisguiseCommand(disguiseManager));
        getServer().getPluginManager().registerEvents(new DisguiseListener(disguiseManager), this);

        //invsee
        getCommand("invsee").setExecutor(new InvseeCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println(Colour.colour("&cPlugin disabled!"));
    }
}
