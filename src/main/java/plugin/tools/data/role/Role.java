package plugin.tools.data.role;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.util.Vector;
import plugin.environment.mission.MissionType;

import java.util.HashMap;
import java.util.UUID;

public interface Role {

    HashMap<UUID, Role> roles = new HashMap<>();

    void setInventory();

    void use(Vector v);

    default void openSetting(Player p) {

    }

    default void openMap(Player p, MissionType... missions) {

    }

    class RoleEvent implements Listener {

        @EventHandler
        public void onDeath(PlayerDeathEvent e) {
            e.setDeathMessage(null);
        }
    }
}