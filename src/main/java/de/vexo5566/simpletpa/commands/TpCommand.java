package de.vexo5566.simpletpa.commands;

import de.vexo5566.simpletpa.SimpleTPA;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TpCommand implements CommandExecutor {

    private final SimpleTPA plugin;

    public TpCommand(SimpleTPA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (!player.hasPermission("simpletpa.tp")) {
            player.sendMessage(plugin.msg("messages.no_permission"));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("§cUsage: /tp <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(plugin.msg("messages.player_not_found"));
            return true;
        }

        player.teleport(target.getLocation());
        player.sendMessage(plugin.msg("messages.teleporting"));
        return true;
    }
}
