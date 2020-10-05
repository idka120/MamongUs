package plugin.tools.data.role;

import org.bukkit.entity.Player;
import plugin.environment.mission.MissionType;

public interface Role {

    void setInventory(Player p);

    default void openSetting(Player p) {

    }

    default void openMap(Player p, MissionType... missions) {

    }
}
