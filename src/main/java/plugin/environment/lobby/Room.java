package plugin.environment.lobby;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import plugin.tools.SectionSetter;
import plugin.tools.data.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("ALL")
public class Room implements Listener {

    public static final HashMap<String, RoomData> data = new HashMap<>();
    protected static final List<String> codes = new ArrayList<>();
    private static String[] l = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static boolean controller;
    private static final Inventory setting = Bukkit.createInventory(null, 27, "§e방 생성");

    private static void stop() throws InterruptedException {
        throw new InterruptedException();
    }

    public static void thread() {
        new Thread(() -> {
            for (int x = 1;; x++) {
                for (int y = 1; y <= 5; y++) {
                    try {
                        stop();
                    }catch (InterruptedException ex) {
                        try {
                            while (!controller) {
                                Thread.sleep(100000);
                            }
                        }catch (InterruptedException e) { }
                    }
                }
            }
        }, "StructerManager").start();
    }

    public static void createRoom(UUID owner) {

    }

    private void create(UUID owner, String name) {
        if(SectionSetter.data.get(name) == null) return;

    }

    @EventHandler
    public void onClickInv(InventoryClickEvent e) {
        if(e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null) {
            if (e.getView().getTitle().equals("§e방 생성")) {
                int data = e.getCurrentItem().getItemMeta().getCustomModelData();
                GameType type = GameType.parseGameType(data);
                switch (e.getCurrentItem().getType()) {
                    case ACACIA_BOAT:
                        if(data == 5) {
                            e.getCurrentItem().getItemMeta().setCustomModelData(1);
                        }else {
                            e.getCurrentItem().getItemMeta().setCustomModelData(data + 1);
                        }
                        e.setCancelled(true);
                        break;
                    case ACACIA_DOOR:
                        if(data == 6) {
                            e.getCurrentItem().getItemMeta().setCustomModelData(1);
                        }else {
                            e.getCurrentItem().getItemMeta().setCustomModelData(data + 1);
                        }
                        e.setCancelled(true);
                        break;
                    case ACACIA_FENCE_GATE:
                        if(e.getCurrentItem().getAmount() == type.getMaxPlayer()) {
                            e.getCurrentItem().setAmount(type.getMinPlayer());
                        }else {
                            e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() + 1);
                        }
                        e.setCancelled(true);
                        break;
                    case BARRIER:
                        String code = Code.getCode(6, false);
                        codes.add(code);
                        Room.data.put(code, new RoomData(e.getInventory().getItem(16).getAmount(), e.getWhoClicked().getUniqueId(), code, type.getImposterCount()));

                        e.setCancelled(true);
                        break;
                    default:
                        e.setCancelled(true);
                        break;
                }
            }
        }
    }
}
