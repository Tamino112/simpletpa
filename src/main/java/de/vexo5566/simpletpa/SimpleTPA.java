package de.vexo5566.simpletpa;

import de.vexo5566.simpletpa.commands.*;
import de.vexo5566.simpletpa.manager.TPAManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleTPA extends JavaPlugin {

    private static SimpleTPA instance;
    private TPAManager tpaManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        tpaManager = new TPAManager();

        getCommand("tpa").setExecutor(new TpaCommand(this));
        getCommand("tpahere").setExecutor(new TpHereCommand(this));
        getCommand("tpaccept").setExecutor(new TpAcceptCommand(this));
        getCommand("tpadeny").setExecutor(new TpDenyCommand(this));
        getCommand("tpatoggle").setExecutor(new TpToggleCommand(this));
        getCommand("tp").setExecutor(new TpCommand(this));

        sendStartupMessage();
    }

    @Override
    public void onDisable() {
        sendShutdownMessage();
    }

    public static SimpleTPA getInstance() {
        return instance;
    }

    public TPAManager getTpaManager() {
        return tpaManager;
    }

    // ✅ Farbcode-Fix für Prefix + Nachricht
    public String msg(String path) {
        String prefix = getConfig().getString("messages.prefix", "");
        String message = getConfig().getString(path, path);
        return (prefix + message).replace("&", "§");
    }

    // ✅ Sound abspielen bei TPA-Anfrage
    public void playTpaSound(Player player) {
        if (!getConfig().getBoolean("sound.enabled")) return;

        String soundName = getConfig().getString("sound.type", "ENTITY_EXPERIENCE_ORB_PICKUP").toUpperCase();
        float volume = (float) getConfig().getDouble("sound.volume", 1.0);
        float pitch = (float) getConfig().getDouble("sound.pitch", 1.0);

        Sound sound = null;
        for (Sound s : Sound.values()) {
            if (s.name().equalsIgnoreCase(soundName)) {
                sound = s;
                break;
            }
        }

        if (sound == null) {
            getLogger().warning("⚠️ Sound '" + soundName + "' not found! Using fallback: ENTITY_EXPERIENCE_ORB_PICKUP");
            sound = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
        }

        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    // ✅ Schöne Konsolenmeldung beim Start
    private void sendStartupMessage() {
        getServer().getConsoleSender().sendMessage("§b====================================");
        getServer().getConsoleSender().sendMessage("§7           §bPLUGIN STARTED");
        getServer().getConsoleSender().sendMessage("§7           §fPlugin: §aSimpleTPA");
        getServer().getConsoleSender().sendMessage("§7           §fVersion: §a" + getDescription().getVersion());
        getServer().getConsoleSender().sendMessage("§7           §fAuthor: §a" + String.join(", ", getDescription().getAuthors()));
        getServer().getConsoleSender().sendMessage("§b====================================");
    }

    // ✅ Schöne Konsolenmeldung beim Stop
    private void sendShutdownMessage() {
        getServer().getConsoleSender().sendMessage("§c====================================");
        getServer().getConsoleSender().sendMessage("§7           §cPLUGIN STOPPED");
        getServer().getConsoleSender().sendMessage("§7           §fPlugin: §aSimpleTPA");
        getServer().getConsoleSender().sendMessage("§7           §fVersion: §a" + getDescription().getVersion());
        getServer().getConsoleSender().sendMessage("§7           §fAuthor: §a" + String.join(", ", getDescription().getAuthors()));
        getServer().getConsoleSender().sendMessage("§c====================================");
    }
}
