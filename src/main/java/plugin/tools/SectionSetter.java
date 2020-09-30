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
import plugin.game.Direction;

import java.util.HashMap;

public class SectionSetter implements Listener {

    ItemStack stack = Setter.SectionSetter;

    private Location loc1;
    private Location loc2;

    public static HashMap<String, SectionData> data = new HashMap<>();



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
                            data.put(data.size() + "", new SectionData(loc1, loc2));
                            sender.sendMessage("섹션이 저장되었습니다!");
                        }
                    case "show" :
                        if(data.size() > 0) {
                            data.forEach((string, sectionData) -> sender.sendMessage("섹션 " + string + "의 첫번째 위치 ("+ (int) sectionData.getPos1().getX() + ", " + (int) sectionData.getPos1().getY() + ", " + (int) sectionData.getPos1().getZ() + ") 와 두번째 위치 ("+ (int) sectionData.getPos2().getX() + ", " + (int) sectionData.getPos2().getY() + ", " + (int) sectionData.getPos2().getZ() + ")"));
                        }else {
                            sender.sendMessage("아직 section가 지정되어있지 않습니다");
                        }
                }
            }else if(args.length == 2) {
                if ("set".equals(args[0])) {
                    if (data.get(args[1]) != null && data.containsKey(args[1])) {
                        sender.sendMessage(args[1] + "이라는 이름의 섹션이 변경되었습니다");
                        data.put(args[1], data.get(args[1]).setSection(loc1, loc2));
                    } else {
                        sender.sendMessage("알 수 없는 이름의 섹션입니다");
                    }
                }
            }else if(args.length == 3) {
                switch (args[0]) {
                    case "autoLoad":

                }
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(" ")) {
            if (e.getHand() == EquipmentSlot.HAND) {
                if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§e위치 설정") && e.getClickedBlock().getLocation() != loc1) {
                        loc1 = e.getClickedBlock().getLocation();
                        p.sendMessage("첫번째 위치가 " + loc1.getX() + ", " + loc1.getY() + ", " + loc1.getZ() + "로 지정되었습니다");
                        e.setCancelled(true);
                    }
                }else if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
                    if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§e위치 설정") && e.getClickedBlock().getLocation() != loc2) {
                        loc2 = e.getClickedBlock().getLocation();
                        p.sendMessage("두번째 위치가 " + loc2.getX() + ", " + loc2.getY() + ", " + loc2.getZ() + "로 지정되었습니다");
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
