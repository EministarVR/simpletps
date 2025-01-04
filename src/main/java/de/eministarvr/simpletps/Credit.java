package de.eministarvr.simpletps;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;

public class Credit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        // Main message
        TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&',
                "&8&l» &6Plugin Credits &8» &7Developed by &aEministarVR\n" +
                        "&8&m-----------------------------\n" +
                        "&7&lThank you for using SimpleTps! Special thanks to:\n" +
                        "&7- &aEministarVR &7for the development\n" +
                        "&7- &eThe community &7for their valuable feedback\n" +
                        "&8&m-----------------------------\n" +
                        "&7Join our &6Discord &7for support: &r&nhttps://discord.gg/8UZkN7MzkF\n" +
                        "&8&m-----------------------------"
        ));

        // Make the Discord link clickable
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/8UZkN7MzkF"));

        // Send the message
        player.spigot().sendMessage(message);

        return true;
    }
}
