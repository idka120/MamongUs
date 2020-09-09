package plugin.game;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class Events implements Listener {

    @EventHandler
    public void onClickNPC(PlayerInteractAtEntityEvent e) {
        if(e.getRightClicked().getType() == EntityType.PLAYER ) {

        }
    }
}
