package plugin.tools.data.role;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import plugin.main.ItemBuild;
import plugin.main.MamongUs;
import plugin.tools.data.Code;

import java.util.*;

public class Imposter implements Role {

    private final List<Material> list = Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR);
    private final ItemBuild ib = new ItemBuild();
    private final HashMap<Vector, Boolean> isAllow = new HashMap<>();
    private final HashMap<String, Boolean> isSaving = new HashMap<>();

    private final Player p;

    private int coolTime = 10;
    private final int CoolTime;

    public static final List<ArmorStand> armorStands = new ArrayList<>();

    public Imposter(Player p, int coolTime) {
        this.p = p;
        this.CoolTime = coolTime;
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

    public Location toLocation(Location loc) {
        return new Location(loc.getWorld(), (int) loc.getX(), (int) loc.getY(),(int) loc.getZ(),(int) loc.getYaw(), (int) loc.getPitch());
    }

    private Vector toVector(Location loc) {
        return new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public void check(Location where, int range, LivingEntity target) {
        String code = Code.getCode(10, false);
        if(coolTime <= 0) return;
        Location targetLoc = target.getLocation();
        new Thread(() -> {
            isSaving.put(code, true);
            boolean east = true;
            boolean west = true;
            boolean north = true;
            boolean south = true;

            boolean b1 = true;
            boolean b2 = true;
            for (int i = 1; i <= range; i++) { //한번에 십자가 체크
                if(list.contains(toLocation(where).add(-i, 0, 0).getBlock().getType())) {//west(-x)
                    for (int j = 1; j <= range-i; j++) {
                        if(!list.contains(toLocation(where).add(-i, 0, -j).getBlock().getType())) { //north(-z)
                            b1 = false;
                        }
                        Vector v = new Vector(-i, 0, -j);
                        if(isAllow.containsKey(v)) {
                            if(isAllow.get(v)) isAllow.put(v, b1);
                        }else {
                            isAllow.put(v, b1);
                        }
                        if(!list.contains(toLocation(where).add(-i, 0, j).getBlock().getType())) { //south(+z)
                            b2 = false;
                        }
                        Vector vec = new Vector(-i, 0, j);
                        if(isAllow.containsKey(vec)) {
                            if(isAllow.get(vec)) isAllow.put(vec, b2);
                        }else {
                            isAllow.put(vec, b2);
                        }
                    }
                }else {
                    west = false;
                    for (int j = 1; j <= range-i; j++) {
                        if(!list.contains(toLocation(where).add(-i, 0, -j).getBlock().getType())) { //north(-z)
                            b1 = false;
                        }
                        Vector v = new Vector(-i, 0, -j);
                        if(isAllow.containsKey(v)) {
                            if(isAllow.get(v)) isAllow.put(v, b1);
                        }else {
                            isAllow.put(v, b1);
                        }
                        if(!list.contains(toLocation(where).add(-i, 0, j).getBlock().getType())) { //south(+z)
                            b2 = false;
                        }
                        Vector vec = new Vector(-i, 0, j);
                        if(isAllow.containsKey(vec)) {
                            if(isAllow.get(vec)) isAllow.put(vec, b2);
                        }else {
                            isAllow.put(vec, b2);
                        }
                    }
                }
                isAllow.put(new Vector(-i, 0, 0), west);
                //초기화
                b1 = true;
                b2 = true;
                //------------------------------------------------------
                if(list.contains(toLocation(where).add(i, 0, 0).getBlock().getType())) { //east(+x)
                    for (int j = 1; j <= range - i; j++) {
                        //north(-z)
                        if (!list.contains(toLocation(where).add(-i, 0, -j).getBlock().getType())) {
                            b1 = false;
                        }
                        Vector v = new Vector(-i, 0, -j);
                        if(isAllow.containsKey(v)) {
                            if(isAllow.get(v)) isAllow.put(v, b1);
                        }else {
                            isAllow.put(v, b1);
                        }

                        //south(+z)
                        if (!list.contains(toLocation(where).add(-i, 0, j).getBlock().getType())) {
                            b2 = false;
                        }
                        Vector vec = new Vector(-i, 0, j);
                        if(isAllow.containsKey(vec)) {
                            if(isAllow.get(vec)) isAllow.put(vec, b2);
                        }else {
                            isAllow.put(vec, b2);
                        }
                    }
                }
                else {
                    east = false;
                    for (int j = 1; j <= range - i; j++) {
                        //north(-z)
                        if (!list.contains(toLocation(where).add(-i, 0, -j).getBlock().getType())) {
                            b1 = false;
                        }
                        Vector v = new Vector(-i, 0, -j);
                        if(isAllow.containsKey(v)) {
                            if(isAllow.get(v)) isAllow.put(v, b1);
                        }else {
                            isAllow.put(v, b1);
                        }

                        //south(+z)
                        if (!list.contains(toLocation(where).add(-i, 0, j).getBlock().getType())) {
                            b2 = false;
                        }
                        Vector vec = new Vector(-i, 0, j);
                        if(isAllow.containsKey(vec)) {
                            if(isAllow.get(vec)) isAllow.put(vec, b2);
                        }else {
                            isAllow.put(vec, b2);
                        }
                    }
                }
                isAllow.put(new Vector(i, 0, 0), east);
                //초기화
                b1 = true;
                b2 = true;
                //-------------------------------------------------------
                if(list.contains(toLocation(where).add(0, 0, -i).getBlock().getType())) { //north (-z)
                    for (int j = 1; j <= range - i; j++) {
                        if (!list.contains(toLocation(where).add(-j, 0, -i).getBlock().getType())) { //west(-x)
                            b1 = false;
                        }
                        Vector v = new Vector(-j, 0, -i);
                        if(isAllow.containsKey(v)) {
                            if(isAllow.get(v)) isAllow.put(v, b1);
                        }else {
                            isAllow.put(v, b1);
                        }
                        if (!list.contains(toLocation(where).add(j, 0, -i).getBlock().getType())) { //east(+x)
                            b2 = false;
                        }
                        Vector vec = new Vector(j, 0, -i);
                        if(isAllow.containsKey(vec)) {
                            if(isAllow.get(vec)) isAllow.put(vec, b2);
                        }else {
                            isAllow.put(vec, b2);
                        }
                    }
                }
                else {
                    north = false;
                    for (int j = 1; j <= range - i; j++) {
                        if (!list.contains(toLocation(where).add(-j, 0, -i).getBlock().getType())) { //west(-x)
                            b1 = false;
                        }
                        Vector v = new Vector(-j, 0, -i);
                        if(isAllow.containsKey(v)) {
                            if(isAllow.get(v)) isAllow.put(v, b1);
                        }else {
                            isAllow.put(v, b1);
                        }
                        if (!list.contains(toLocation(where).add(j, 0, -i).getBlock().getType())) { //east(+x)
                            b2 = false;
                        }
                        Vector vec = new Vector(j, 0, -i);
                        if(isAllow.containsKey(vec)) {
                            if(isAllow.get(vec)) isAllow.put(vec, b2);
                        }else {
                            isAllow.put(vec, b2);
                        }
                    }
                }
                isAllow.put(new Vector(0, 0, -i), north);
                //초기화
                b1 = true;
                b2 = true;
                //-------------------------------------------------------
                if(list.contains(toLocation(where).add(0, 0, i).getBlock().getType())) {//south(+z)
                    for (int j = 1; j <= range - i; j++) {
                        if (!list.contains(toLocation(where).add(-j, 0, i).getBlock().getType())) { //west(-x)
                            b1 = false;
                        }
                        Vector v = new Vector(-j, 0, i);
                        if(isAllow.containsKey(v)) {
                            if(isAllow.get(v)) isAllow.put(v, b1);
                        }else {
                            isAllow.put(v, b1);
                        }
                        if (!list.contains(toLocation(where).add(j, 0, i).getBlock().getType())) { //east(+x)
                            b2 = false;
                        }
                        Vector vec = new Vector(j, 0, i);
                        if(isAllow.containsKey(vec)) {
                            if(isAllow.get(vec)) isAllow.put(vec, b2);
                        }else {
                            isAllow.put(vec, b2);
                        }
                    }
                } else {
                    south = false;
                    for (int j = 1; j <= range - i; j++) {
                        if (!list.contains(toLocation(where).add(-j, 0, i).getBlock().getType())) { //west(-x)
                            b1 = false;
                        }
                        Vector v = new Vector(-j, 0, i);
                        if(isAllow.containsKey(v)) {
                            if(isAllow.get(v)) isAllow.put(v, b1);
                        }else {
                            isAllow.put(v, b1);
                        }
                        if (!list.contains(toLocation(where).add(j, 0, i).getBlock().getType())) { //east(+x)
                            b2 = false;
                        }
                        Vector vec = new Vector(j, 0, i);
                        if(isAllow.containsKey(vec)) {
                            if(isAllow.get(vec)) isAllow.put(vec, b2);
                        }else {
                            isAllow.put(vec, b2);
                        }
                    }
                }
                isAllow.put(new Vector(0, 0, i), south);
            }
            //모든 검사가 끝난뒤
            //try {
                //if (isAllow.get(toVector(targetLoc.add(-where.getBlockX(), -targetLoc.getBlockY(), -where.getBlockZ())))) {
                    //kill(target);
                //}
            //}catch (NullPointerException ex) {/*catch로 잡았지만 뭐 없음*/}
        }).start();
        Bukkit.getScheduler().runTask(MamongUs.getPlugin(), () -> isAllow.forEach((vector, Boolean) -> {
            if(Boolean) {
                where.getWorld().getBlockAt(toLocation(where).add(vector)).setType(Material.GREEN_STAINED_GLASS, false);
            }else {
                where.getWorld().getBlockAt(toLocation(where).add(vector)).setType(Material.RED_STAINED_GLASS, false);
            }
        }));
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

    public void CoolTime() {
        coolTime = CoolTime;
        new Thread(() -> {
            while(coolTime <= 0) {
                try {
                    coolTime -= 1;
                    p.getInventory().getItem(2).setAmount(coolTime);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.err.println("오류발생!");
                }
            }
            coolTime = 0;
        }).start();
    }

    public void kill(LivingEntity target) {
        Bukkit.getScheduler().runTask(MamongUs.getPlugin(), () -> {
            //대쉬
            p.setVelocity(toVector(toLocation(target.getLocation()).subtract(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ())));
            CoolTime();
            //deadbody생성
            ArmorStand deadBody = (ArmorStand) target.getWorld().spawnEntity(target.getLocation(), EntityType.ARMOR_STAND);
            deadBody.setMarker(false);
            deadBody.setGravity(false);
            deadBody.setVisible(false);
            deadBody.getEquipment().setHelmet(target.getEquipment().getHelmet());
            //내려가게 하기
            Bukkit.getScheduler().runTaskLater(MamongUs.getPlugin(), () -> {
                try { //1.225칸 내려감....
                    double how = 1.225;
                    int many = 10;
                    for (int i = 0; i <= many; i++) {
                        deadBody.teleport(deadBody.getLocation().add(0, -(how / many), 0));
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    //그런데 짜잔! 아무것도 없었습니다.
                }
            }, 1);
            death(target);
        });
    }


    public void killTest() {
        //deadbody생성
        ArmorStand deadBody = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
        deadBody.setMarker(false); deadBody.setGravity(false); deadBody.setVisible(false);
        deadBody.getEquipment().setHelmet(p.getEquipment().getHelmet());
        //내려가게 하기
        Bukkit.getScheduler().runTaskLater(MamongUs.getPlugin(), () -> {
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
        }, 1);
        death(p);
    }

    public void useSabotage(Player p) {
        Bukkit.getServer().dispatchCommand(p, "sabo open");
    }
}
