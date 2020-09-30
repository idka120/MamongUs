package plugin.environment; //오류날 부분

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import plugin.main.MamongUs;
import plugin.main.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Vent implements Listener {

    public static HashMap<UUID, Boolean> isInVent = new HashMap<>();
    public static HashMap<String, List<Location>> locations = new HashMap<>();

    private static final List<String> location = new ArrayList<>();

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        PlayerData data = MamongUs.data.get(p.getUniqueId());
        if(/* data.getRole() == Role.Imposter && */ p.getWorld().getBlockAt((int) p.getLocation().getX(), (int) p.getLocation().getY() - 1, (int) p.getLocation().getZ()).getType() == Material.IRON_TRAPDOOR) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999, 10, false, false), false);
            isInVent.put(p.getUniqueId(), true);
            ItemStack stack = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE, 1);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(" ");
            stack.setItemMeta(meta);

            p.setGameMode(GameMode.SPECTATOR);

            EulerAngle ea = new EulerAngle(90, 0, 0);
            
            List<Entity> l = new ArrayList<>();

            p.getInventory().setItem(0, stack);
            p.getInventory().setItem(1, stack);
            p.getInventory().setItem(2, stack);
            p.getInventory().setItem(6, stack);
            p.getInventory().setItem(7, stack);
            p.getInventory().setItem(8, stack);
        }
    }

    @EventHandler
    public void onHeld(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();
        int i = e.getNewSlot();
        //if(isInVent.get(p.getUniqueId())) {
            //if (i == 3) { //왼쪽

            //} else if (i == 5) { //오른쪽

            //} else if (i == 4) { //나가기
            //    isInVent.remove(p.getUniqueId());
            //} else {
            //    e.setCancelled(true);
            //}
        //}
    }

    @EventHandler
    public void onTP(PlayerTeleportEvent e) {
        PlayerTeleportEvent.TeleportCause cause = e.getCause();
        if(cause == PlayerTeleportEvent.TeleportCause.SPECTATE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD && e.getClickedBlock().getType() == Material.IRON_TRAPDOOR && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getHand() == EquipmentSlot.HAND) {
            Player p = e.getPlayer();
            Block b = e.getClickedBlock();

            String str = p.getUniqueId().toString() + ":" + p.getWorld().getName() + ":" + (b.getX() + 0.5) + ":" + (b.getY() + 3) + ":" + (b.getZ() + 0.5);
            if(!location.contains(str)) {
                location.add(str);
                p.sendMessage("§a벤트의 위치가 지정되었습니다. 연결되는 벤트를 모두 지정하셨다면 @vent <name> 해 주세요");
            }else {
                p.sendMessage("§c이미 저장되있는 벤트입니다");
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        if(message.indexOf("@") == 0 && message.indexOf("vent") == 1) {
            message = message.replace("@vent", " ").trim();
            String name = message;
            List<Location> locs = new ArrayList<>();
            List<String> strings = new ArrayList<>();
            location.forEach(l -> {
                String[] element = l.split(":");
                if (element[0].equals(e.getPlayer().getUniqueId().toString())) {
                    World w = Bukkit.getWorld(element[1]);
                    double x = Double.parseDouble(element[2]);
                    double y = Double.parseDouble(element[3]);
                    double z = Double.parseDouble(element[4]);
                    e.getPlayer().sendMessage(w.getName() + "의 " + x + ", " + y + ", " + z +  "에 있는 벤트가 저장되었습니다");
                    Location loc = new Location(w, x, y, z);
                    strings.add(l);
                    locs.add(loc);
                }
            });
            location.removeAll(strings);
            locations.put(name, locs);
            e.setCancelled(true);
        }
    }
}
