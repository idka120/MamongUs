package plugin.tools.data.role;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.HashMap;
import java.util.UUID;

public class Events implements Listener {

    public static final HashMap<UUID, Role> roles = new HashMap<>();

    @EventHandler
    public void onHeld(PlayerItemHeldEvent e) {
        Role role = roles.get(e.getPlayer().getUniqueId());
    }
}
