package plugin.tools.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import plugin.main.MamongUs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class SectionData {

    public static boolean isSaving = true;

    private final HashMap<Integer, HashMap<Integer, HashMap<Integer, Block>>> blocks = new HashMap<>();
    private Location pos1;
    private Location pos2;

    private boolean b;
    private int i;
    private final HashMap<Integer, HashMap<Integer, Block>> mapOne = new HashMap<>();
    private final HashMap<Integer, Block> mapTwo = new HashMap<>();

    private Queue<Block> block = new LinkedList<>();
    private Queue<Integer> X = new LinkedList<>();
    private Queue<Integer> Y = new LinkedList<>();
    private Queue<Integer> Z = new LinkedList<>();

    //곧 삭제될 큐
    private Queue<Integer> sX = new LinkedList<>();
    private Queue<Integer> sY = new LinkedList<>();
    private Queue<Integer> sZ = new LinkedList<>();

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
        isSaving = true;
        Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
            try {
                for (int x = pos1.getBlockX(); x <= pos2.getBlockX(); x++) {
                    System.out.println("x값 저장됨(" + x + ")");
                    for (int y = pos1.getBlockY(); y <= pos2.getBlockY(); y++) {
                        System.out.println("y값 저장됨(" + y + ")");
                        for (int z = pos1.getBlockZ(); z <= pos2.getBlockZ(); z++) {
                            System.out.println("z값 저장됨(" + z + ")");
                            mapTwo.put(z - (int) pos1.getZ(), pos1.getWorld().getBlockAt(x, y, z));
                            mapOne.put(y - (int) pos1.getY(), mapTwo);
                            blocks.put(x - (int) pos1.getX(), mapOne);
                            sX.add(x);
                            sY.add(y);
                            sZ.add(z);
                            Thread.sleep(1);
                        }
                    }
                }
            }catch (InterruptedException e) {

            }
        });
        Bukkit.getScheduler().runTask(MamongUs.getPlugin(), () -> {
            if(sX.size() != 0) {
                pos1.getWorld().getBlockAt(sX.poll(), sY.poll(), sZ.poll()).setType(Material.ORANGE_STAINED_GLASS);
            }
        });
        isSaving = false;
    }

    private void load(Location loc) {
        while (true) {
            if(!isSaving) {
                Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
                    for (Integer x : blocks.keySet()) {
                        for (Integer y: blocks.get(x).keySet()) {
                            for (Integer z: blocks.get(x).get(y).keySet()) {
                                try {
                                    this.block.add(blocks.get(x).get(y).get(z));
                                    X.add(loc.getBlockX() + x);
                                    Y.add(loc.getBlockY() + y);
                                    Z.add(loc.getBlockZ() + z);
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                }
                            }
                        }
                    }
                });
                Bukkit.getScheduler().runTask(MamongUs.getPlugin(), () -> {
                    if (block.size() != 0) {
                        //loc.getWorld().getBlockAt(X.peek(), Y.peek(), Z.peek()).setType(block.peek().getType());
                        //loc.getWorld().getBlockAt(X.peek(), Y.peek(), Z.peek()).setBlockData(block.poll().getBlockData());
                        loc.getWorld().getBlockAt(X.poll(), Y.poll(), Z.poll()).setType(Material.ORANGE_STAINED_GLASS, false);
                    }
                });
                break;
            }
        }
    }

    public int getBlockCount() {
        while (true) {
            if(!isSaving) {
                i = 0;
                Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet()) {
                                i += 1;
                            }
                });
                break;
            }
        }
        return i;
    }

    public boolean isFilled(Material type) {
        while(true) {
            if(!isSaving) {
                Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
                    b = true;
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet()) {
                                if (blocks.get(x).get(y).get(z).getType() != type) b = false;
                            }
                });
                break;
            }
        }
        return b;
    }

    public boolean isFilled(Block b) {
        while (true) {
            if(isSaving) {
                Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
                    this.b = true;
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                if (blocks.get(x).get(y).get(z) != b) this.b = false;
                });
                break;
            }
        }
        return this.b;
    }

    public boolean contain(Material type) {
        while (true) {
            if(!isSaving) {
                Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
                    this.b = false;
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                if (blocks.get(x).get(y).get(z).getType() == type) b = true;
                });
                break;
            }
        }
        return b;
    }

    public boolean contains(Block b) {
        while (true) {
            if(!isSaving) {
                Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
                    this.b = false;
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                if (blocks.get(x).get(y).get(z) == b)
                                    this.b = true;
                });
                break;
            }
        }
        return this.b;
    }

    public boolean isVoid() {
        while (true) {
            if(!isSaving) {
                Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
                    b = true;
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                if (blocks.get(x).get(y).get(z).getType() != Material.VOID_AIR)
                                    b = false;
                });
                break;
            }
        }
        return b;
    }

    public boolean isAir() {
        while (true) {
            if(!isSaving) {
                Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
                    b = true;
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                if (blocks.get(x).get(y).get(z).getType() != Material.AIR && blocks.get(x).get(y).get(z).getType() != Material.CAVE_AIR)
                                    this.b = false;
                });
                break;
            }
        }
        return b;
    }

    public void show(LivingEntity sender, Location loc) {
        while (true) {
            System.out.println("while탐지");
            if(!isSaving) {
                System.out.println("if 탐지");
                Bukkit.getScheduler().runTask(MamongUs.getPlugin(),() -> {
                    System.out.println("스케듈려 탐지");
                    for (Integer x : blocks.keySet()) {
                        System.out.println("x값 불러옴(" + x + ")");
                        for (Integer y: blocks.get(x).keySet()) {
                            System.out.println("y값 불러옴(" + x + ")");
                            for (Integer z: blocks.get(x).get(y).keySet()) {
                                System.out.println("z값 불러옴(" + x + ")");
                                sender.sendMessage("x : " + (loc.getBlockX() + x) + ", y : " + (loc.getBlockY() + y) + ", z : " + (loc.getBlockZ() + z));
                            }
                        }
                    }
                });
                break;
            }
        }
    }

    public void load(Location loc, boolean force, String direction, LivingEntity sender) {
        while (true) {
            if (!isSaving) {
                try {
                    Direction dir = Direction.toDirection(direction);
                    if (!force) {
                        loop:
                        for (int i = 0; ; i++) {
                            switch (dir) {
                                case SOUTH:
                                    if (isAir()) {
                                        load(loc.add(0, 0, (Math.abs(pos1.getBlockZ() - pos2.getBlockZ()) + 1) * i));
                                        break loop;
                                    }
                                    break;
                                case NORTH:
                                    if (isFilled(Material.AIR)) {
                                        load(loc.add(0, 0, (-(Math.abs(pos1.getBlockZ() - pos2.getBlockZ()) + 1)) * i));
                                        break loop;
                                    }
                                    break;
                                case EAST:
                                    if (isFilled(Material.AIR)) {
                                        load(loc.add((Math.abs(pos1.getBlockX() - pos2.getBlockX()) + 1) * i, 0, 0));
                                        break loop;
                                    }
                                    break;
                                case WEST:
                                    if (isFilled(Material.AIR)) {
                                        load(loc.add((-(Math.abs(pos1.getBlockX() - pos2.getBlockX()) + 1)) * i, 0, 0));
                                        break loop;
                                    }
                                    break;
                                case UP:
                                    if (isFilled(Material.AIR)) {
                                        load(loc.add((Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1) * i, 0, 0));
                                        break loop;
                                    }
                                    break;
                                case DOWN:
                                    if (isFilled(Material.AIR)) {
                                        load(loc.add(-(Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1) * i, 0, 0));
                                        break loop;
                                    }
                                    break;
                            }
                        }
                    } else {
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
                break;
            }
        }
    }
}
