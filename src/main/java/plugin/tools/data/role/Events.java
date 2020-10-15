package plugin.tools.data.role;

import org.bukkit.GameRule;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Events implements Listener {

    @EventHandler
    public void onHeld(PlayerItemHeldEvent e) {
        if(Role.roles.containsKey(e.getPlayer().getUniqueId())) {
            Player p = e.getPlayer();
            Role role = Role.roles.get(e.getPlayer().getUniqueId());
            int slot = e.getNewSlot();
            switch (slot) {
                case 1:

                    break;
                case 2:
                    if(role instanceof Imposter) {
                        p.getWorld().setGameRule(GameRule.DO_ENTITY_DROPS, false);
                        try {
                            final Queue<LivingEntity> targets = new LinkedList<>();
                            p.getNearbyEntities(3, 3, 3).forEach(entity -> {
                                if (entity instanceof LivingEntity && entity != p && !(entity instanceof ArmorStand)) targets.add((LivingEntity) entity);
                            });
                            ((Imposter) role).check(p.getLocation(), 3, targets.poll());
                        }catch (NullPointerException ex) {}
                    }
                    break;
            }
            p.getInventory().setHeldItemSlot(0);
        }
    }
}
