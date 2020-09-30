package plugin.tools;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import plugin.game.Direction;

import java.util.HashMap;

public class SectionData {

    private HashMap<Integer, HashMap<Integer, HashMap<Integer, Block>>> blocks = new HashMap<>();
    private Location pos1;
    private Location pos2;

    private boolean b;
    private int i;
    private HashMap<Integer, HashMap<Integer, Block>> mapOne = new HashMap<>();
    private HashMap<Integer, Block> mapTwo = new HashMap<>();

    public SectionData(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        save();
    }

    public SectionData setSection(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        save();
        return this;
    }

    public Location getPos1() { return pos1; }
    public Location getPos2() { return pos2; }

    private void save() { //pos1이 큰거 pos2가 작은거(?)
        new Thread(() -> {
            try {
                for (int x = (int) pos1.getX(); x < (int) pos2.getX(); x++) {
                    for (int y = (int) pos1.getY(); y < (int) pos2.getY(); y++) {
                        for (int z = (int) pos1.getZ(); z < (int) pos2.getZ(); z++) {
                            mapTwo.put(z - (int) pos1.getZ(), pos1.getWorld().getBlockAt(x, y, z));
                            mapOne.put(y - (int) pos1.getY(), mapTwo);
                            blocks.put(x - (int) pos1.getX(), mapOne);
                            pos1.getWorld().getBlockAt(x, y, z).setType(Material.ORANGE_STAINED_GLASS);
                            Thread.sleep(250);
                        }
                    }
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void load(Location loc) {
        new Thread(() -> blocks.forEach((kx, vy) -> vy.forEach((ky, vz) -> vz.forEach((kz, block) -> {
            try {
                loc.getWorld().getBlockAt(kx + (int) loc.getX(), ky + (int) loc.getY(), kz + (int) loc.getZ()).setType(block.getType());
                loc.getWorld().getBlockAt(kx + (int) loc.getX(), ky + (int) loc.getY(), kz + (int) loc.getZ()).setBlockData(block.getBlockData());
                loc.getWorld().getBlockAt(kx + (int) loc.getX(), ky + (int) loc.getY(), kz + (int) loc.getZ()).setType(Material.YELLOW_STAINED_GLASS);
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })))).start();
    }

    public int getBlockCount() {
        i = 0;
        new Thread(() -> {
            for (Integer x : blocks.keySet())
                for (Integer y : blocks.get(x).keySet())
                    for (Integer z : blocks.get(x).get(y).keySet()) {
                        i += 1;
                    }
        }).start();
        return i;
    }

    public boolean isFilled(Material type) {
        new Thread(() -> {
            b = true;
            for (Integer x : blocks.keySet())
                for (Integer y : blocks.get(x).keySet())
                    for (Integer z : blocks.get(x).get(y).keySet()) {
                        if (blocks.get(x).get(y).get(z).getType() != type) b = false;
                    }
        }).start();
        return b;
    }

    public boolean isFilled(Block b) {
        new Thread(() -> {
            this.b = true;
            for (Integer x : blocks.keySet())
                for (Integer y : blocks.get(x).keySet())
                    for (Integer z : blocks.get(x).get(y).keySet())
                        if (blocks.get(x).get(y).get(z) != b) this.b = false;
        }).start();
        return this.b;
    }

    public boolean contain(Material type) {
        new Thread(() -> {
            this.b = true;
            for (Integer x : blocks.keySet())
                for (Integer y : blocks.get(x).keySet())
                    for (Integer z : blocks.get(x).get(y).keySet())
                        if (blocks.get(x).get(y).get(z).getType() == type) b = false;
        }).start();
        return b;
    }

    public boolean contains(Block b) {
        new Thread(() -> {
            this.b = true;
            for (Integer x : blocks.keySet())
                for (Integer y : blocks.get(x).keySet())
                    for (Integer z : blocks.get(x).get(y).keySet())
                        if (blocks.get(x).get(y).get(z) == b)
                            this.b = false;
        }).start();
        return this.b;
    }

    public boolean isVoid() {
        new Thread(() -> {
            b = true;
            for (Integer x : blocks.keySet())
                for (Integer y : blocks.get(x).keySet())
                    for (Integer z : blocks.get(x).get(y).keySet())
                        if (blocks.get(x).get(y).get(z).getType() != Material.VOID_AIR)
                            b = false;
        }).start();
        return b;
    }

    public boolean isAir() {
        new Thread(() -> {
            b = true;
            for (Integer x : blocks.keySet())
                for (Integer y : blocks.get(x).keySet())
                    for (Integer z : blocks.get(x).get(y).keySet())
                        if (blocks.get(x).get(y).get(z).getType() != Material.AIR && blocks.get(x).get(y).get(z).getType() != Material.CAVE_AIR)
                            this.b = false;
        }).start();
        return b;
    }

    public void show(LivingEntity sender) {
        new Thread(() -> {
            blocks.forEach((kx, vy) -> vy.forEach((ky, vz) -> vz.forEach((kz, block) -> {
                sender.sendMessage("x : " + kx + ",y : " + ky + ",z : " + kz);
            })));
        }).start();
    }

    public void load(Location loc, boolean force, String direction, LivingEntity sender) {
        try {
            Direction dir = Direction.toDirection(direction);
            if (!force) {
                loop : for(int i = 0;; i++) {
                    switch (dir) {
                        case SOUTH:
                            if(isAir()) {
                                load(loc.add(0, 0, (Math.abs(pos1.getBlockZ() - pos2.getBlockZ()) + 1) * i));
                                break loop;
                            }
                            break;
                        case NORTH:
                            if(isFilled(Material.AIR)) {
                                load(loc.add(0, 0, (-(Math.abs(pos1.getBlockZ() - pos2.getBlockZ()) + 1)) * i));
                                break loop;
                            }
                            break;
                        case EAST:
                            if(isFilled(Material.AIR)) {
                                load(loc.add((Math.abs(pos1.getBlockX() - pos2.getBlockX()) + 1) * i, 0, 0));
                                break loop;
                            }
                            break;
                        case WEST:
                            if(isFilled(Material.AIR)) {
                                load(loc.add((-(Math.abs(pos1.getBlockX() - pos2.getBlockX()) + 1)) * i, 0, 0));
                                break loop;
                            }
                            break;
                        case UP:
                            if(isFilled(Material.AIR)) {
                                load(loc.add((Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1) * i, 0, 0));
                                break loop;
                            }
                            break;
                        case DOWN:
                            if(isFilled(Material.AIR)) {
                                load(loc.add(-(Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1) * i, 0, 0));
                                break loop;
                            }
                            break;
                    }
                }
            }else {
                switch (dir) {
                    case SOUTH:
                        load(loc.add(0, 0, Math.abs(pos1.getBlockZ() - pos2.getBlockZ()) + 1));
                        break;
                    case NORTH:
                        load(loc.add(0, 0, -(Math.abs(pos1.getBlockZ() - pos2.getBlockZ()) + 1)));
                        break;
                    case EAST:
                        load(loc.add(Math.abs(pos1.getBlockX() - pos2.getBlockX()) + 1, 0, 0));
                        break;
                    case WEST:
                        load(loc.add(-(Math.abs(pos1.getBlockX() - pos2.getBlockX()) + 1), 0, 0));
                        break;
                    case UP:
                        load(loc.add(Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1, 0, 0));
                        break;
                    case DOWN:
                        load(loc.add(-(Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1), 0, 0));
                        break;
                }
            }
        } catch (IllegalArgumentException ex) {
            sender.sendMessage("§c알 수 없는 방향입니다");
        }
    }
}
