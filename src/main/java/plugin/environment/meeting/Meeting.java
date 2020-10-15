package plugin.environment.meeting;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import plugin.tools.data.RoomData;
import plugin.tools.data.role.Imposter;

import java.util.ArrayList;
import java.util.List;

public class Meeting {

    public void start() {

    }

    public void report(Player p, RoomData room) {
        final List<ArmorStand> deadBodies = new ArrayList<>();
        p.getNearbyEntities(6, 6, 6).forEach(entity -> {
            if(entity instanceof ArmorStand && Imposter.armorStands.contains(entity)) {
                deadBodies.add((ArmorStand) entity);
                return;
            }
        });
        if(deadBodies.size() <= 0) return;
        deadBodies.get(0);

    }
}
