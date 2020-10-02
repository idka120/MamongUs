package plugin.main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import plugin.main.MamongUs;
import plugin.tools.data.PlayerData;

public class Events implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        MamongUs.data.put(e.getPlayer().getUniqueId(), new PlayerData());
    }
}
