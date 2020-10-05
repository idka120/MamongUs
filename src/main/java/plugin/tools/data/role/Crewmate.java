package plugin.tools.data.role;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugin.main.ItemBuild;

public class Crewmate implements Role {

    ItemBuild ib = new ItemBuild();

    @Override
    public void setInventory(Player p) {
        Inventory inv = p.getInventory();
        inv.setItem(1, ib.setItem(Material.WHITE_STAINED_GLASS_PANE, "§fMap", null).setCustomModelData(1).getItem());
        inv.setItem(2, ib.setItem(Material.ORANGE_STAINED_GLASS_PANE, "§fSetting", null).setCustomModelData(5).getItem());
        for (int i = 0; i < 9; i++) {
            if(inv.getItem(i) == null) {
                inv.setItem(i, ib.setItem(Material.YELLOW_STAINED_GLASS_PANE, " ", null).getItem());
            }
        }
        inv.setItem(0, new ItemStack(Material.AIR));
    }
}
