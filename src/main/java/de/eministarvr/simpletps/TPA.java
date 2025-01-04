package de.eministarvr.simpletps;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TPA implements CommandExecutor {

    private final Map<UUID, UUID> tpaRequests = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        // Handle /tpa command
        if (label.equalsIgnoreCase("tpa")) {
            return handleTpaCommand(player, args);
        }

        // Handle /tpaccept command
        if (label.equalsIgnoreCase("tpaccept")) {
            return handleTpAccept(player);
        }

        // Handle /tpdeny command
        if (label.equalsIgnoreCase("tpdeny")) {
            return handleTpDeny(player);
        }

        return false;
    }

    public boolean handleTpaCommand(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage("Usage: /tpa <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            player.sendMessage("✘ Player not found or not online.");
            return true;
        }

        if (target.equals(player)) {
            player.sendMessage("✘ You cannot send a TPA request to yourself.");
            return true;
        }

        // Send TPA request
        tpaRequests.put(target.getUniqueId(), player.getUniqueId());

        TextComponent message = new TextComponent("╔═══════════════════════════════════╗\n");
        message.addExtra(new TextComponent("   ✨ SimpleTPs Request ✨\n"));
        message.addExtra(new TextComponent("╚═══════════════════════════════════╝\n"));

        TextComponent playerName = new TextComponent(player.getName());
        playerName.setColor(net.md_5.bungee.api.ChatColor.YELLOW);
        playerName.setBold(true);
        message.addExtra(playerName);
        message.addExtra(new TextComponent(" has requested to teleport to you.\n"));

        TextComponent accept = new TextComponent("[✓ Accept]");
        accept.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        accept.setBold(true);
        accept.setItalic(true);
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
        message.addExtra(accept);

        TextComponent deny = new TextComponent(" [✗ Deny]");
        deny.setColor(net.md_5.bungee.api.ChatColor.RED);
        deny.setBold(true);
        deny.setItalic(true);
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny"));
        message.addExtra(deny);

        // Send the TPA request message to the target using spigot() method
        target.spigot().sendMessage(message);

        // Send confirmation to the player
        player.sendMessage("╔═══════════════════════════════════╗\n" +
                "   ✨ SimpleTPs Info ✨\n" +
                "╚═══════════════════════════════════╝\n" +
                "Your request to " + target.getName() + " has been sent.\n");

        return true;
    }

    public boolean handleTpAccept(Player player) {
        UUID requesterUUID = tpaRequests.remove(player.getUniqueId());
        if (requesterUUID == null) {
            player.sendMessage("✘ You have no pending TPA requests.");
            return true;
        }

        Player requester = Bukkit.getPlayer(requesterUUID);
        if (requester == null || !requester.isOnline()) {
            player.sendMessage("✘ The player who sent the request is no longer online.");
            return true;
        }

        // Teleport the requester to the target
        requester.teleport(player.getLocation());
        player.sendMessage("✔ You have accepted the TPA request from " + requester.getName() + ".");
        requester.sendMessage("✔ Your TPA request to " + player.getName() + " has been accepted.");

        return true;
    }

    public boolean handleTpDeny(Player player) {
        UUID requesterUUID = tpaRequests.remove(player.getUniqueId());
        if (requesterUUID == null) {
            player.sendMessage("✘ You have no pending TPA requests.");
            return true;
        }

        Player requester = Bukkit.getPlayer(requesterUUID);
        if (requester != null && requester.isOnline()) {
            requester.sendMessage("✘ Your TPA request to " + player.getName() + " has been denied.");
        }

        player.sendMessage("✔ You have denied the TPA request.");
        return true;
    }
}
