package plugin.tools.data.role;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import plugin.main.ItemBuild;

public class Imposter implements Role {

    private ItemBuild ib = new ItemBuild();

    @Override
    public void setInventory(Player p) {
        Inventory inv = p.getInventory();
        //inv.setItem(0, );
    }
}
