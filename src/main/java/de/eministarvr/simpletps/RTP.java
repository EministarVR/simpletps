package de.eministarvr.simpletps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;

import java.util.Random;

public class RTP implements CommandExecutor {

    private final Random random = new Random();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§8[§aSimpleTps§8] §cThis command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        // Sending message to the player about the teleportation process
        player.sendMessage("§8[§aSimpleTps§8] §7Looking for a safe location... please hold on!");

        // Find a safe location
        Location safeLocation = findSafeLocation(player.getWorld());
        if (safeLocation == null) {
            player.sendMessage("§8[§aSimpleTps§8] §cOops! We couldn't find a safe place. Please try again.");
            return true;
        }

        // Teleport player to the safe location
        player.teleport(safeLocation);
        player.sendMessage("§8[§aSimpleTps§8] §7You have been safely teleported to a random location! 🎉");

        // Send a clickable message with the » arrow and "teleport again" functionality
        TextComponent message = new TextComponent("§8[§aSimpleTps§8] §7You have been safely teleported! ");
        TextComponent arrow = new TextComponent("» ");
        arrow.setColor(net.md_5.bungee.api.ChatColor.GOLD);
        arrow.setBold(true);
        arrow.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rtp"));

        message.addExtra(arrow);
        message.addExtra(new TextComponent("Click here to teleport again!"));
        message.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        message.setBold(true);

        player.spigot().sendMessage(message);

        return true;
    }

    // Method to find a safe location
    private Location findSafeLocation(org.bukkit.World world) {
        int x = random.nextInt(10000) - 5000; // Random X coordinate
        int z = random.nextInt(10000) - 5000; // Random Z coordinate

        // Loop to find a safe Y coordinate (above ground level)
        for (int y = 255; y > 50; y--) {
            Location location = new Location(world, x, y, z);
            Block block = location.getBlock();
            Material blockType = block.getType();

            // Check if the location is safe (not inside blocks like stone, lava, etc.)
            if (isSafeBlock(blockType)) {
                // Ensure the player will not spawn inside a solid block (i.e., check the block above)
                if (location.add(0, 1, 0).getBlock().getType().isAir()) {
                    return location;
                }
            }
        }
        return null; // If no safe location is found
    }

    // Method to check if the block is safe for teleportation (can be customized)
    private boolean isSafeBlock(Material block) {
        return block == Material.AIR || block == Material.GRASS_BLOCK || block == Material.SAND || block == Material.DIRT || block == Material.WATER;
    }
}
