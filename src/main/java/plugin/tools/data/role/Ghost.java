package plugin.tools.data.role;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import plugin.main.ItemBuild;

public class Ghost implements Role {

    private final Player p;
    private ItemBuild ib = new ItemBuild();

    public Ghost(Player p) {
        this.p = p;
        p.sendTitle("§c사망하셨습니다", "§e당신은 이제 관전자 입니다", 10, 60, 20);
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2, 1, false, false, false));
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999999, 1, false, false, false));
        p.setInvulnerable(true);
        p.setAllowFlight(true);
        Role.roles.put(p.getUniqueId(), this);
    }

    @Override
    public void setInventory() {
        for (int i = 0; i < 9; i++) {
            p.getInventory().setItem(i, ib.setItem(Material.WHITE_STAINED_GLASS_PANE, " ", null).getItem());
        }
        p.getInventory().setItem(0, null);
    }

    @Override
    public void use(Vector v) {

    }
}
