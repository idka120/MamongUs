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

import java.util.*;

@SuppressWarnings("ALL")
public class LocationSetter implements Listener {

    private List<Location> locations = new ArrayList<>();
    private ItemStack stack = Setter.LocationSetter;
    private Location previous;

    public static Location crew;
    public static String direction;
    public static Location imposter;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        Player sender = e.getPlayer();

        String message = e.getMessage();
        if (message.indexOf("@") == 0) {
            if (message.indexOf("location") == 1) {
                message = message.replace("@location", " ").trim();
                String[] args = message.split(" ");
                if (args.length == 1) { //기본세팅은 west이다
                    if (args[0].equalsIgnoreCase("crew")) { //@location crew
                        crew = previous;
                        direction = "west";
                        sender.sendMessage("위치(x : " + previous.getX() + ", y : " + previous.getY() + ", z : " + previous.getZ() + ") 가 crew의 소개장소로 지정되었습니다");
                    } else if (args[0].equalsIgnoreCase("imposter")) {
                        imposter = previous;
                        direction = "west";
                        sender.sendMessage  ("위치(x : " + previous.getX() + ", y : " + previous.getY() + ", z : " + previous.getZ() + ") 가 imposeter의 소개장소로 지정되었습니다");
                    } else if (args[0].equalsIgnoreCase("show")) {
                        if(locations.size() > 0) {
                            locations.forEach(l -> {
                                sender.sendMessage("x : " + l.getX() + ", y : " + l.getY() + ", z : " + l.getZ());
                            });
                        }else {
                            sender.sendMessage("저장되어있는 좌표가 없습니다");
                        }
                    }
                }else if (args.length == 2) {
                    try {
                        if (args[0].equalsIgnoreCase("remove")) {
                            if (args[1].equalsIgnoreCase("previous")) {
                                locations.remove(previous);
                                sender.sendMessage("이전의 위치(x : " + previous.getX() + ", y : " + previous.getY() + ", z : " + previous.getZ() + ") 가 제거되었습니다");
                            } else {
                                int number = Integer.parseInt(args[1]);
                                Location loc = locations.get(number + 1);
                                sender.sendMessage(number + "에 있던 위치(x : " + loc.getX() + ", y : " + loc.getY() + ", z : " + loc.getZ() + ") 가 list에서 제거되었습니다");
                                locations.remove(loc);
                            }
                        } else if (args[0].equalsIgnoreCase("show")) {
                            int number = Integer.parseInt(args[1]);
                            Location loc = locations.get(number + 1);
                            sender.sendMessage(number + "번째의 위치 : {x : " + loc.getX() + ", y : " + loc.getY() + ", z : " + loc.getZ() + "}");
                        } else if (args[0].equalsIgnoreCase("crew")) {
                            this.crew = previous;
                            this.direction = args[1];
                            sender.sendMessage("위치(x : " + previous.getX() + ", y : " + previous.getY() + ", z : " + previous.getZ() + ") 가 crew의 소개장소로 지정되었습니다");
                        } else if (args[0].equalsIgnoreCase("imposter")) {
                            this.imposter = previous;
                            this.direction = args[1];
                            sender.sendMessage("위치(x : " + previous.getX() + ", y : " + previous.getY() + ", z : " + previous.getZ() + ") 가 imposter의 소개장소로 지정되었습니다");
                        }
                    } catch (Exception ex) {
                        if(ex instanceof NumberFormatException) sender.sendMessage("§c숫자를 입력해 주세요");
                        if(ex instanceof ArrayIndexOutOfBoundsException) sender.sendMessage("§c그 위치에 값이 저장되어 있지 않습니다");
                    }
                }
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getItemMeta() != null) {
            if (e.getHand() == EquipmentSlot.HAND) {
                if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§e위치 설정")) {
                        locations.add(e.getClickedBlock().getLocation());
                        p.sendMessage("위치가 저장되었습니다");
                        previous = e.getClickedBlock().getLocation();
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
