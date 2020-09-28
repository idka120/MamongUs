package plugin.tools;

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

    public SectionData(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public SectionData setSection(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        return this;
    }

    public Location getPos1() { return pos1; }
    public Location getPos2() { return pos2; }

    public void save() { //pos1이 큰거 pos2가 작은거(?)
        HashMap<Integer, HashMap<Integer, Block>> mapOne = new HashMap<>();
        HashMap<Integer, Block> mapTwo = new HashMap<>();

        for (int x = (int) pos1.getX(); x < (int) pos2.getX(); x++) {
            for (int y = (int) pos1.getY(); y < (int) pos2.getY(); y++) {
                for (int z = (int) pos1.getZ(); z < (int) pos2.getZ(); z++) {
                    mapTwo.put(z - (int) pos1.getZ(), pos1.getWorld().getBlockAt(x, y, z));
                    mapOne.put(y - (int) pos1.getY(), mapTwo);
                    blocks.put(x - (int) pos1.getZ(), mapOne);
                }
            }
        }
    }

    private void load(Location loc) {
        blocks.forEach((kx, vy) -> vy.forEach((ky, vz) -> vz.forEach((kz, block) -> {
            loc.getWorld().getBlockAt(kx + (int) loc.getX(), ky + (int) loc.getY(), kz + (int) loc.getZ()).setType(block.getType());
            loc.getWorld().getBlockAt(kx + (int) loc.getX(), ky + (int) loc.getY(), kz + (int) loc.getZ()).setBlockData(block.getBlockData());
        })));
    }

    public int getBlockCount() {
        int count = 0;
        for(Integer x : blocks.keySet()) for(Integer y : blocks.get(x).keySet()) for(Integer z : blocks.get(x).get(y).keySet()) {
            count += 1;
        }
        return count;
    }

    public boolean isFilled(Material type) {
        for(Integer x : blocks.keySet()) for(Integer y : blocks.get(x).keySet()) for(Integer z : blocks.get(x).get(y).keySet()) {
            if(blocks.get(x).get(y).get(z).getType() != type) return false;
        }
        return true;
    }

    public boolean isFilled(Block b) {
        for(Integer x : blocks.keySet()) for(Integer y : blocks.get(x).keySet()) for(Integer z : blocks.get(x).get(y).keySet()) {
            if(blocks.get(x).get(y).get(z) != b) return false;
        }
        return true;
    }

    public boolean contain(Material type) {
        for(Integer x : blocks.keySet()) for(Integer y : blocks.get(x).keySet()) for(Integer z : blocks.get(x).get(y).keySet()) {
            if(blocks.get(x).get(y).get(z).getType() == type) return true;
        }
        return false;
    }

    public boolean contains(Block b) {
        for(Integer x : blocks.keySet()) for(Integer y : blocks.get(x).keySet()) for(Integer z : blocks.get(x).get(y).keySet()) {
            if(blocks.get(x).get(y).get(z) == b) return true;
        }
        return false;
    }

    public boolean isVoid() {
        for(Integer x : blocks.keySet()) for(Integer y : blocks.get(x).keySet()) for(Integer z : blocks.get(x).get(y).keySet()) {
            if(blocks.get(x).get(y).get(z).getType() != Material.VOID_AIR) return false;
        }
        return true;
    }

    public boolean isAir() {
        for(Integer x : blocks.keySet()) for(Integer y : blocks.get(x).keySet()) for(Integer z : blocks.get(x).get(y).keySet()) {
            if(blocks.get(x).get(y).get(z).getType() != Material.AIR && blocks.get(x).get(y).get(z).getType() != Material.CAVE_AIR) return false;
        }
        return true;
    }

    public void load(Location loc, boolean force, String direction, LivingEntity sender) {
        try {
            Direction dir = Direction.toDirection(direction);
            if (!force) {
                loop : for(int i = 0;; i++) {
                    switch (dir) {
                        case SOUTH:
                            loc.add(0, 0, (Math.abs(pos1.getBlockZ() - pos2.getBlockZ()) + 1) * i);
                            if(isAir()) {
                                load(loc);
                                break loop;
                            }
                            break;
                        case NORTH:
                            loc.add(0, 0, (-(Math.abs(pos1.getBlockZ() - pos2.getBlockZ()) + 1)) * i);
                            if(isFilled(Material.AIR)) {
                                load(loc);
                                break loop;
                            }
                            break;
                        case EAST:
                            loc.add((Math.abs(pos1.getBlockX() - pos2.getBlockX()) + 1) * i, 0, 0);
                            if(isFilled(Material.AIR)) {
                                load(loc);
                                break loop;
                            }
                            break;
                        case WEST:
                            loc.add((-(Math.abs(pos1.getBlockX() - pos2.getBlockX()) + 1)) * i, 0, 0);
                            if(isFilled(Material.AIR)) {
                                load(loc);
                                break loop;
                            }
                            break;
                        case UP:
                            loc.add((Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1) * i, 0, 0);
                            if(isFilled(Material.AIR)) {
                                load(loc);
                                break loop;
                            }
                            break;
                        case DOWN:
                            loc.add(    -(Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1) * i, 0, 0);
                            if(isFilled(Material.AIR)) {
                                load(loc);
                                break loop;
                            }
                            break;
                    }
                }
            }else {
                switch (dir) {
                    case SOUTH:
                        loc.add(0, 0, Math.abs(pos1.getBlockZ() - pos2.getBlockZ()) + 1);
                        break;
                    case NORTH:
                        loc.add(0, 0, -(Math.abs(pos1.getBlockZ() - pos2.getBlockZ()) + 1));
                        break;
                    case EAST:
                        loc.add(Math.abs(pos1.getBlockX() - pos2.getBlockX()) + 1, 0, 0);
                        break;
                    case WEST:
                        loc.add(-(Math.abs(pos1.getBlockX() - pos2.getBlockX()) + 1), 0, 0);
                        break;
                    case UP:
                        loc.add(Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1, 0, 0);
                        break;
                    case DOWN:
                        loc.add(-(Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1), 0, 0);
                        break;
                }
            }
        } catch (IllegalArgumentException ex) {
            sender.sendMessage("§c알 수 없는 방향입니다");
        }
    }
}
