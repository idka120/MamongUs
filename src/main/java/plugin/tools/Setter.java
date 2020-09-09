package plugin.tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import plugin.main.ItemBuild;

import java.util.Arrays;

public class Setter implements Listener {

    private final Inventory inv = Bukkit.createInventory(null, 45, "§7Tools");

    public static ItemStack VentSetter;
    public static ItemStack LocationSetter;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        if(message.indexOf("@") == 0) {
            if (message.indexOf("setter") == 1) {
                //기본 인벤토리 세팅
                ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE);
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(" ");
                stack.setItemMeta(meta);
                stack.setData(new MaterialData(15));


                ItemBuild ib = new ItemBuild(); //아이템빌드 객체 생성

                inv.setItem(4, ib.setItem(Material.SIGN, "§7Tools - Setter", Arrays.asList("§f다양한 도구를 만나보세요!")).getItem());
                inv.setItem(40, ib.setItem(Material.BARRIER, "§c나가기", Arrays.asList("§f툴박스를 나갑니다")).getItem()); //36, 37, 38,

                //인벤토리에 넣기
                for (int i = 0; i < 45; i++)
                    if (inv.getItem(i) == null && (i < 9 || 36 <= i || i % 9 == 0 || (i + 1) % 9 == 0))
                        inv.setItem(i, stack);
                VentSetter = ib.setItem(Material.BLAZE_ROD, "§e벤트 설정", Arrays.asList("§f벤트에 관하여 설정하세요", " ", "§e[§f우클릭(철 다락문)§e] §f벤트의 위치를 설정합니다", "§c[§f관련 명령어§c] §f@vent <name> ")).addEnchantment(Enchantment.DAMAGE_ALL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).getItem();
                inv.setItem(10, VentSetter);
                LocationSetter = ib.setItem(Material.BLAZE_ROD, "§e위치 설정", Arrays.asList("§f위치를 설정하세요", " ", "§e[§f우클릭(모든블록)§e] §f그 블록의 위치를 가져오고 저장합니다", "§c[§f관련 명령어§c] §f@location <usage>")).addEnchantment(Enchantment.DAMAGE_ALL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).getItem();
                inv.setItem(11, LocationSetter);
                e.getPlayer().openInventory(inv);
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Material m = e.getCurrentItem().getType();
        if(e.getView().getTitle().equals("§7Tools")) {
            switch (m) {
                case STAINED_GLASS_PANE:
                case SIGN:
                case AIR:
                    e.setCancelled(true);
                    break;
                case BARRIER:
                    e.setCancelled(true);
                    p.closeInventory();
                    break;
                default:
                    e.setCancelled(true);
                    p.getInventory().addItem(e.getCurrentItem());
                    break;
        }
        }
    }
}
