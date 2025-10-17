package de.vexo5566.simpletpa.commands;

import de.vexo5566.simpletpa.SimpleTPA;
import de.vexo5566.simpletpa.manager.TPAManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpHereCommand implements CommandExecutor {

    private final SimpleTPA plugin;

    public TpHereCommand(SimpleTPA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("§cUsage: /tpahere <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            player.sendMessage(plugin.msg("messages.player_not_found"));
            return true;
        }

        if (player.equals(target)) {
            player.sendMessage(plugin.msg("messages.cannot_send_to_self"));
            return true;
        }

        TPAManager.TpaRequest request = new TPAManager.TpaRequest(player, target, true);
        plugin.getTpaManager().addRequest(request);

        player.sendMessage(plugin.msg("messages.tpa_here_sent").replace("%target%", target.getName()));
        target.sendMessage(plugin.msg("messages.tpa_here_received").replace("%sender%", player.getName()));

        // ✅ Klickbare Buttons
        TextComponent accept = new TextComponent("§a[ACCEPT]");
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to accept teleport request").create()));
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));

        TextComponent deny = new TextComponent(" §c[DENY]");
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to deny teleport request").create()));
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpadeny"));

        target.spigot().sendMessage(accept, deny);

        plugin.playTpaSound(target);
        return true;
    }
}
