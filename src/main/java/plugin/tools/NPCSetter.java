package plugin.tools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class NPCSetter implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Material m = e.getClickedBlock().getType();
        Location loc = e.getClickedBlock().getLocation();
        Action a = e.getAction();
        EquipmentSlot slot = e.getHand();
        ItemStack stack = p.getInventory().getItemInMainHand();
        if(a == Action.LEFT_CLICK_BLOCK && slot == EquipmentSlot.HAND && ) {

        }
    }

    @EventHandler
    public void onClickEntity(PlayerInteractEntityEvent e) {

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

    }
}
