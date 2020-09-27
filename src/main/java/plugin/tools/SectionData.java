package plugin.tools;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import plugin.game.Direction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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

    public void load(Location loc) {
        blocks.forEach((kx, vy) -> vy.forEach((ky, vz) -> vz.forEach((kz, block) -> {
            loc.getWorld().getBlockAt(kx + (int) loc.getX(), ky + (int) loc.getY(), kz + (int) loc.getZ()).setType(block.getType());
            loc.getWorld().getBlockAt(kx + (int) loc.getX(), ky + (int) loc.getY(), kz + (int) loc.getZ()).setBlockData(block.getBlockData());
        })));
    }

    public void load(Location loc, boolean force, String direction, LivingEntity sender) {
        if (force) {
            try {
                Direction dir = Direction.toDirection(direction);
                boolean b = true;
                while (b) {
                    switch (dir) {
                        case SOUTH:
                            int i = Math.abs(pos1.getBlockX() - pos2.getBlockX()) + 1;
                    }
                }
            } catch (IllegalArgumentException ex) {
                sender.sendMessage("§c알 수 없는 방향입니다");
            }
        }
    }
}
