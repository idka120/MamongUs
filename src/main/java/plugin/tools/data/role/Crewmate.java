package plugin.tools.data.role;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import plugin.main.ItemBuild;

import java.util.Arrays;

public class Crewmate implements Role {

    private final Player p;

    ItemBuild ib = new ItemBuild();

    public Crewmate(Player p) {
        this.p = p;
        Role.roles.put(p.getUniqueId(), this);
    }

    @Override
    public void setInventory() {
        Inventory inv = p.getInventory();
        inv.setItem(1, ib.setItem(Material.WHITE_STAINED_GLASS_PANE, "§fReport", Arrays.asList("§e주변의 시체를 리포트 합니다")).setCustomModelData(2).getItem());
        inv.setItem(7, ib.setItem(Material.WHITE_STAINED_GLASS_PANE, "§fMap", null).setCustomModelData(1).getItem());
        inv.setItem(8, ib.setItem(Material.ORANGE_STAINED_GLASS_PANE, "§fSetting", null).setCustomModelData(5).getItem());
        for (int i = 0; i < 9; i++) if(inv.getItem(i) == null) inv.setItem(i, ib.setItem(Material.YELLOW_STAINED_GLASS_PANE, " ", null).getItem());
        inv.setItem(0, new ItemStack(Material.AIR));
    }

    @Override
    public void use(Vector v) {

    }
}
