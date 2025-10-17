package de.vexo5566.simpletpa.commands;

import de.vexo5566.simpletpa.SimpleTPA;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TpToggleCommand implements CommandExecutor {

    private final SimpleTPA plugin;

    public TpToggleCommand(SimpleTPA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        plugin.getTpaManager().toggle(player);

        if (plugin.getTpaManager().isToggledOff(player)) {
            player.sendMessage(plugin.msg("messages.tpa_toggled_off"));
        } else {
            player.sendMessage(plugin.msg("messages.tpa_toggled_on"));
        }
        return true;
    }
}
