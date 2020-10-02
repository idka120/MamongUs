package plugin.environment.lobby;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import plugin.tools.data.GameType;
import plugin.tools.data.RoomData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Room implements Listener {

    public static final HashMap<String, RoomData> data = new HashMap<>();
    public static final List<String> codes = new ArrayList<>();
    private static String[] l = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private static final Inventory setting = Bukkit.createInventory(null, 27, "§e방 생성");

    public static void createRoom(UUID owner) {

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
                        String code = l[(int) (Math.random() * l.length)]
                            + l[(int) (Math.random() * l.length)]
                            + l[(int) (Math.random() * l.length)]
                            + l[(int) (Math.random() * l.length)]
                            + l[(int) (Math.random() * l.length)]
                            + l[(int) (Math.random() * l.length)];
                        codes.add(code);
                        Room.data.put(code, new RoomData(e.getCurrentItem().getAmount(), e.getWhoClicked().getUniqueId(), code, type.getImposterCount()));
                        e.setCancelled(true);
                        break;
                    default:
                        e.setCancelled(true);
                }
            }
        }
    }
}
