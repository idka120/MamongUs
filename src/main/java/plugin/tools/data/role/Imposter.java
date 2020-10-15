package plugin.tools.data.role;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import plugin.main.ItemBuild;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Imposter implements Role {

    private final List<Material> list = Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR);
    private final ItemBuild ib = new ItemBuild();
    private final HashMap<Vector, Boolean> isAllow = new HashMap<>();

    private final Player p;

    public static final List<ArmorStand> armorStands = new ArrayList<>();

    public Imposter(Player p) {
        this.p = p;
        Role.roles.put(p.getUniqueId(), this);
    }

    @Override
    public void setInventory() {
        final Inventory inv = p.getInventory();
        inv.setItem(1, ib.setItem(Material.WHITE_STAINED_GLASS_PANE, "§fReport", Arrays.asList("§e주변의 시체를 리포트 합니다")).setCustomModelData(2).getItem());
        inv.setItem(2, ib.setItem(Material.ORANGE_STAINED_GLASS_PANE, "§cKill", Arrays.asList("§e주변의 플레이어를 죽입니다")).setCustomModelData(3).getItem());
        inv.setItem(3, ib.setItem(Material.ORANGE_STAINED_GLASS_PANE, "§cVent", Arrays.asList("§e벤트를 탑니다")).setCustomModelData(4).getItem());
        inv.setItem(4, ib.setItem(Material.ORANGE_STAINED_GLASS_PANE, "§cSabotage", Arrays.asList("§e사보타지를 엽니다")).setCustomModelData(5).getItem());
        inv.setItem(7, ib.setItem(Material.WHITE_STAINED_GLASS_PANE, "§fMap", Arrays.asList("§e지도를 엽니다")).setCustomModelData(1).getItem());
        inv.setItem(8, ib.setItem(Material.ORANGE_STAINED_GLASS_PANE, "§fSetting", Arrays.asList("§e설정창을 엽니다")).setCustomModelData(6).getItem());
        for (int i = 0; i < 9; i++) if(inv.getItem(i) == null) inv.setItem(i,ib.setItem(Material.YELLOW_STAINED_GLASS_PANE, " ", null).getItem());
        inv.setItem(0, new ItemStack(Material.AIR));
    }

    @Override
    public void use(Vector v) {

    }

    private Vector toVector(Location loc) {
        return new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public void check(Location where, int range, LivingEntity target) {
        Location targetLoc = target.getLocation();
        //new Thread(() -> {
            boolean east = true;
            boolean west = true;
            boolean north = true;
            boolean south = true;
            for (int i = 1; i <= range; i++) { //한번에 십자가 체크
                if(list.contains(where.add(-i, 0, 0).getBlock().getType())) {//west(-x)
                    where.add(-i, 0, 0).getBlock().setType(Material.GREEN_STAINED_GLASS);
                    for (int j = 1; j <= range-i; j++) {
                        if(!list.contains(where.add(-i, 0, -j).getBlock().getType())) { //north(-z)
                            north = false;
                            where.add(-i, 0, -j).getBlock().setType(Material.RED_STAINED_GLASS);
                        }else {
                            where.add(-i, 0, j).getBlock().setType(Material.GREEN_STAINED_GLASS);
                        }
                        isAllow.put(new Vector(-i, 0, -j), north);
                        if(!list.contains(where.add(-i, 0, j).getBlock().getType())) { //south(+z)
                            south = false;
                            where.add(-i, 0, j).getBlock().setType(Material.RED_STAINED_GLASS);
                        }else {
                            where.add(-i, 0, j).getBlock().setType(Material.GREEN_STAINED_GLASS);
                        }
                        isAllow.put(new Vector(-i, 0, j), south);
                    }
                }else {
                    west = false;
                    where.add(-i, 0, 0).getBlock().setType(Material.RED_STAINED_GLASS);
                }
                isAllow.put(new Vector(-i, 0, 0), west);
                //초기화
                east = true;
                west = true;
                north = true;
                south = true;
                //------------------------------------------------------
                if(list.contains(where.add(i, 0, 0).getBlock().getType())) { //east(+x)
                    for (int j = 1; j <= range - i; j++) {
                        if (!list.contains(where.add(-i, 0, -j).getBlock().getType())) { //north(-z)
                            north = false;
                        }
                        isAllow.put(new Vector(-i, 0, -j), north);
                        if (!list.contains(where.add(-i, 0, j).getBlock().getType())) { //south(+z)
                            south = false;
                        }
                        isAllow.put(new Vector(-i, 0, j), south);
                    }
                }
                else { east = false; }
                isAllow.put(new Vector(i, 0, 0), east);
                //초기화
                east = true;
                west = true;
                north = true;
                south = true;
                //-------------------------------------------------------
                if(list.contains(where.add(0, 0, -i).getBlock().getType())) { //north (-z)
                    for (int j = 1; j <= range - i; j++) {
                        if (!list.contains(where.add(-j, 0, -i).getBlock().getType())) { //west(-x)
                            west = false;
                        }
                        isAllow.put(new Vector(-j, 0, -i), west);
                        if (!list.contains(where.add(j, 0, -i).getBlock().getType())) { //east(+x)
                            east = false;
                        }
                        isAllow.put(new Vector(j, 0, -i), east);
                    }
                }
                else { north = false;}
                isAllow.put(new Vector(0, 0, -i), north);
                //초기화
                east = true;
                west = true;
                north = true;
                south = true;
                //-------------------------------------------------------
                if(list.contains(where.add(0, 0, i).getBlock().getType())) {//south(+z)
                    for (int j = 1; j <= range - i; j++) {
                        if (!list.contains(where.add(-j, 0, i).getBlock().getType())) { //west(-x)
                            west = false;
                        }
                        isAllow.put(new Vector(-j, 0, i), west);
                        if (!list.contains(where.add(j, 0, i).getBlock().getType())) { //east(+x)
                            east = false;
                        }
                        isAllow.put(new Vector(j, 0, i), east);
                    }
                }
                else { south = false; }
                isAllow.put(new Vector(0, 0, i), south);
            }
            //모든 검사가 끝난뒤
            //if(isAllow.get(toVector(targetLoc.add(-where.getBlockX(), -targetLoc.getBlockY(), -where.getBlockZ())))) {
                kill(target);
            //}
        //}).start();
    }

    private void death(LivingEntity entity) {
        if(entity instanceof Player) {
            Player p = (Player) entity;
            p.setBedSpawnLocation(p.getLocation(), true);
            p.getInventory().clear();
            p.setHealth(0);
            p.spigot().respawn();
            Ghost g = new Ghost(p);
            g.setInventory();
        }else entity.setHealth(0);
    }

    public void kill(LivingEntity target) {
        //deadbody생성
        ArmorStand deadBody = (ArmorStand) target.getWorld().spawnEntity(target.getLocation(), EntityType.ARMOR_STAND);
        deadBody.setMarker(false); deadBody.setGravity(false); deadBody.setVisible(false);
        deadBody.getEquipment().setHelmet(target.getEquipment().getHelmet());
        //내려가게 하기
        new Thread(() -> {
            try { //1.225칸 내려감....
                double how = 1.225;
                int many = 10;
                for (int i = 0; i <= many; i++) {
                    deadBody.teleport(deadBody.getLocation().add(0, -(how / many), 0));
                    Thread.sleep(50);
                }
            }catch (InterruptedException e) {
                //그런데 짜잔! 아무것도 없었습니다.
            }
        }).start();

        death(target);
    }


    public void killTest() {
        kill(p);
    }

    public void useSabotage(Player p) {
        Bukkit.getServer().dispatchCommand(p, "sabo open");
    }
}
