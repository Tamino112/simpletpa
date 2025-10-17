package de.vexo5566.simpletpa.manager;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TPAManager {

    private final HashMap<UUID, TpaRequest> requests = new HashMap<>();
    private final Set<UUID> toggledOff = new HashSet<>(); // Spieler, die TPA blockiert haben

    // ✅ Anfrage hinzufügen
    public void addRequest(TpaRequest request) {
        requests.put(request.getTarget().getUniqueId(), request);
    }

    // ✅ Anfrage entfernen
    public void removeRequest(Player target) {
        requests.remove(target.getUniqueId());
    }

    // ✅ Prüfen, ob Zielspieler eine Anfrage hat
    public boolean hasRequest(Player target) {
        return requests.containsKey(target.getUniqueId());
    }

    // ✅ Den anfragenden Spieler (Sender) zurückgeben
    public Player getRequester(Player target) {
        TpaRequest req = requests.get(target.getUniqueId());
        return req != null ? req.getSender() : null;
    }

    // ✅ Ganze Anfrage zurückgeben
    public TpaRequest getRequest(Player target) {
        return requests.get(target.getUniqueId());
    }

    // ✅ Toggle TPA (an/aus)
    public void toggle(Player player) {
        UUID uuid = player.getUniqueId();
        if (toggledOff.contains(uuid)) {
            toggledOff.remove(uuid); // wieder aktivieren
        } else {
            toggledOff.add(uuid); // blockieren
        }
    }

    // ✅ Prüfen, ob Spieler TPA blockiert hat
    public boolean isToggledOff(Player player) {
        return toggledOff.contains(player.getUniqueId());
    }

    // ✅ Innere Klasse für TPA-Anfragen
    public static class TpaRequest {
        private final Player sender;
        private final Player target;
        private final boolean here; // false = /tpa, true = /tpahere

        public TpaRequest(Player sender, Player target, boolean here) {
            this.sender = sender;
            this.target = target;
            this.here = here;
        }

        public Player getSender() {
            return sender;
        }

        public Player getTarget() {
            return target;
        }

        public boolean isHere() {
            return here;
        }
    }
}
