package plugin.tools;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SectionSetter implements Listener {

    ItemStack stack = Setter.SectionSetter;

    private Location loc1;
    private Location loc2;

    private List<Section> sections;

    private static class Section {

        private Location pos1;
        private Location pos2;
        private Location mainPos;

        public Section(Location loc1, Location loc2) {
            pos1 = new Location(loc1.getWorld(), (int) loc1.getX(), (int) loc1.getY(), (int) loc1.getZ());
            pos2 = new Location(loc2.getWorld(), (int) loc2.getX(), (int) loc2.getY(), (int) loc2.getZ());
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player sender = e.getPlayer();
        String message = e.getMessage();
        if(message.indexOf("@") == 0 && message.indexOf("section") == 1) {
            message = message.replace("@section", " ").trim();
            String[] args = message.split(" ");
            if(args.length == 1) {
                switch (args[0]) {
                    case "add" :
                        if(loc1 == null) {
                            sender.sendMessage("첫번째 위치를 지정해 주세요");
                        }else if(loc2 == null) {
                            sender.sendMessage("두번째 위치를 지정해 주세요");
                        }else {
                            sections.add(new Section(loc1, loc2));
                        }
                    case "show" :
                        if(sections.size() > 0) {

                        }else {
                            sender.sendMessage("아직 위치가 지정되어있지 않습니다");
                        }
                }
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() != null) {
            if (e.getHand() == EquipmentSlot.HAND) {
                if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§e위치 설정")) {
                        loc1 = e.getClickedBlock().getLocation();
                        e.setCancelled(true);
                    }
                }else if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
                    if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§e위치 설정")) {
                        loc2 = e.getClickedBlock().getLocation();
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
