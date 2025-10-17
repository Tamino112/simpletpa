package de.vexo5566.simpletpa.commands;

import de.vexo5566.simpletpa.SimpleTPA;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TpAcceptCommand implements CommandExecutor {

    private final SimpleTPA plugin;

    public TpAcceptCommand(SimpleTPA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player target)) return true;

        if (!plugin.getTpaManager().hasRequest(target)) {
            target.sendMessage(plugin.msg("messages.no_request"));
            return true;
        }

        Player requester = plugin.getTpaManager().getRequester(target);
        if (requester == null) {
            target.sendMessage(plugin.msg("messages.player_not_found"));
            plugin.getTpaManager().removeRequest(target);
            return true;
        }

        target.sendMessage(plugin.msg("messages.tpa_accepted"));
        requester.sendMessage(plugin.msg("messages.teleporting"));
        requester.teleport(target.getLocation());
        plugin.getTpaManager().removeRequest(target);
        return true;
    }
}
