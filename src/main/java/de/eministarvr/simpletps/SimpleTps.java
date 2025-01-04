package de.eministarvr.simpletps;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleTps extends JavaPlugin {

    private TPA tpa;

    @Override
    public void onEnable() {
        // Register the TPA command and its handlers
        this.getCommand("tpa").setExecutor(new TPA());
        this.getCommand("tpaccept").setExecutor(new TPA());
        this.getCommand("tpdeny").setExecutor(new TPA());

        this.getCommand("credit").setExecutor(new Credit());
        this.getCommand("rtp").setExecutor(new RTP());
        this.getCommand("simpletp").setExecutor(new Simpletp());
        getLogger().info("SimpleTPs has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("SimpleTPs has been disabled.");
    }
}
