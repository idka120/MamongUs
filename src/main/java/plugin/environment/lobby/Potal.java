package plugin.environment.lobby;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import plugin.tools.LocationSetter;
import plugin.tools.data.GameType;

import java.util.HashMap;
import java.util.List;

public class Potal implements Listener {

    private Inventory inv = Bukkit.createInventory(null, 54, "");

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if(e.getClickedBlock() != null && LocationSetter.potal.containsKey(e.getClickedBlock().getLocation())) {
            Location loc = e.getClickedBlock().getLocation();

        }
    }
}
