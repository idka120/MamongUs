package plugin.game;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Potal implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Location loc = e.getClickedBlock().getLocation();
    }
}
