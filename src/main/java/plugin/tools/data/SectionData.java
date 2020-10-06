package plugin.tools.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import plugin.main.MamongUs;

import java.util.*;

@SuppressWarnings("ALL")
public class SectionData {

    private static boolean isSaving = true;

    private final HashMap<Integer, HashMap<Integer, HashMap<Integer, Block>>> blocks = new HashMap<>();
    private Location pos1;
    private Location pos2;

    private final HashMap<Integer, HashMap<Integer, Block>> mapOne = new HashMap<>();
    private final HashMap<Integer, Block> mapTwo = new HashMap<>();

    private final HashMap<String, Boolean> b = new HashMap<>();
    private final HashMap<String, Integer> i = new HashMap<>();

    private final Queue<Block> block = new LinkedList<>();
    private final Queue<Integer> X = new LinkedList<>();
    private final Queue<Integer> Y = new LinkedList<>();
    private final Queue<Integer> Z = new LinkedList<>();

    //곧 삭제될 큐
    private final Queue<Integer> sX = new LinkedList<>();
    private final Queue<Integer> sY = new LinkedList<>();
    private final Queue<Integer> sZ = new LinkedList<>();

    public boolean controller;
    public String direction;

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
                isSaving = false;
            }catch (InterruptedException e) {
                System.out.println("오류발생!");
            }
        });
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
                Bukkit.getScheduler().runTaskTimer(MamongUs.getPlugin(), () -> {
                    while(block.size() > 0) {
                        //loc.getWorld().getBlockAt(X.peek(), Y.peek(), Z.peek()).setType(block.peek().getType());
                        //loc.getWorld().getBlockAt(X.peek(), Y.peek(), Z.peek()).setBlockData(block.poll().getBlockData());
                        loc.getWorld().getBlockAt(X.poll(), Y.poll(), Z.poll()).setType(Material.ORANGE_STAINED_GLASS, false);
                    }
                    while(sX.size() > 0) {
                        loc.getWorld().getBlockAt(sX.poll(), sY.poll(), sZ.poll()).setType(Material.ORANGE_STAINED_GLASS, false);
                    }
                },1, 0);
                break;
            }
        }
    }

    public int getBlockCount() {
        String code = Code.getCode(4, false);
        Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
            while (true) {
                if(!isSaving) {
                    int I = 0;
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                I += 1;
                            i.put(code, I);
                            break;
                }
            }
        });
        Code.remove(code);
        int result = i.get(code);
        i.remove(code);
        return result;
    }

    public boolean isFilled(Material type) {
        String code = Code.getCode(4, false);
        Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
        while(true) {
            if(!isSaving) {
                b.put(code, true);
                for (Integer x : blocks.keySet())
                    for (Integer y : blocks.get(x).keySet())
                        for (Integer z : blocks.get(x).get(y).keySet())
                            if (blocks.get(x).get(y).get(z).getType() != type) b.put(code, false);
                            break;
            }
            }
        });
        Code.remove(code);
        boolean result = b.get(code);
        b.remove(code);
        return result;
    }

    public boolean isFilled(Material type, Location loc) {
        String code = Code.getCode(4, false);
        Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
            while(true) {
                if(!isSaving) {
                    b.put(code, true);
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                if(loc.getWorld().getBlockAt(loc.add(x, y, z)).getType() != type) b.put(code, false);
                    break;
                }
            }
        });
        Code.remove(code);
        boolean result = b.get(code);
        b.remove(code);
        return result;
    }

    public boolean isFilled(Block b) {
        String code = Code.getCode(4, false);
        Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
            while(true) {
                if(!isSaving) {
                    this.b.put(code, true);
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                if (blocks.get(x).get(y).get(z) != b) this.b.put(code, false);
                    break;
                }
            }
        });
        Code.remove(code);
        boolean result = this.b.get(code);
        this.b.remove(code);
        return result;
    }

    public boolean isFilled(Block b, Location loc) {
        String code = Code.getCode(4, false);
        Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
            while(true) {
                if(!isSaving) {
                    this.b.put(code, true);
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                if(loc.getWorld().getBlockAt(loc.add(x, y, z)) != b) this.b.put(code, false);
                    break;
                }
            }
        });
        Code.remove(code);
        boolean result = this.b.get(code);
        this.b.remove(code);
        return result;
    }

    public boolean contains(Material type) {
        String code = Code.getCode(4, false);
        Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
            while(true) {
                if(!isSaving) {
                    b.put(code, false);
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                if (blocks.get(x).get(y).get(z).getType() == type) b.put(code, true);
                    break;
                }
            }
        });
        Code.remove(code);
        boolean result = b.get(code);
        b.remove(code);
        return result;
    }

    public boolean contains(Material type, Location loc) {
        String code = Code.getCode(4, false);
        Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
            while(true) {
                if(!isSaving) {
                    b.put(code, false);
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                if(loc.getWorld().getBlockAt(loc.add(x, y, z)).getType() == type) b.put(code, true);
                    break;
                }
            }
        });
        Code.remove(code);
        boolean result = b.get(code);
        b.remove(code);
        return result;
    }

    public boolean contains(Block b) {
        String code = Code.getCode(4, false);
        Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
            while(true) {
                if(!isSaving) {
                    this.b.put(code, false);
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                if (blocks.get(x).get(y).get(z) == b) this.b.put(code, true );
                    break;
                }
            }
        });
        Code.remove(code);
        boolean result = this.b.get(code);
        this.b.remove(code);
        return result;
    }

    public boolean contains(Block b, Location loc) {
        String code = Code.getCode(4, false);
        Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
            while(true) {
                if(!isSaving) {
                    this.b.put(code, false);
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                if(loc.getWorld().getBlockAt(loc.add(x, y, z)) == b) this.b.put(code, true);
                    break;
                }
            }
        });
        Code.remove(code);
        boolean result = this.b.get(code);
        this.b.remove(code);
        return result;
    }

    public boolean isVoid() {
        String code = Code.getCode(4, false);
        Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
            while(true) {
                if(!isSaving) {
                    this.b.put(code, true);
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                if (blocks.get(x).get(y).get(z).getType() != Material.VOID_AIR) this.b.put(code, false);
                    break;
                }
            }
        });
        Code.remove(code);
        boolean result = this.b.get(code);
        b.remove(code);
        return result;
    }

    public boolean isVoid(Location loc) {
        String code = Code.getCode(4, false);
        Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
            while(true) {
                if(!isSaving) {
                    this.b.put(code, true);
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                if (loc.getWorld().getBlockAt(loc.add(x, y, z)).getType() != Material.VOID_AIR) this.b.put(code, false);
                    break;
                }
            }
        });
        Code.remove(code);
        boolean result = this.b.get(code);
        b.remove(code);
        return result;
    }

    public boolean isAir() {
        String code = Code.getCode(4, false);
        Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
            while(true) {
                if(!isSaving) {
                    this.b.put(code, true);
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                if (blocks.get(x).get(y).get(z).getType() != Material.AIR && blocks.get(x).get(y).get(z).getType() != Material.CAVE_AIR) this.b.put(code, false);
                    break;
                }
            }
        });
        Code.remove(code);
        boolean result = this.b.get(code);
        i.remove(code);
        return result;
    }

    public boolean isAir(Location loc) {
        String code = Code.getCode(4, false);
        Bukkit.getScheduler().runTaskAsynchronously(MamongUs.getPlugin(), () -> {
            while(true) {
                if(!isSaving) {
                    this.b.put(code, true);
                    for (Integer x : blocks.keySet())
                        for (Integer y : blocks.get(x).keySet())
                            for (Integer z : blocks.get(x).get(y).keySet())
                                if (loc.getWorld().getBlockAt(loc.add(x, y, z)).getType() != Material.CAVE_AIR && loc.getWorld().getBlockAt(loc.add(x, y, z)).getType() != Material.AIR) this.b.put(code, false);
                    break;
                }
            }
        });
        Code.remove(code);
        boolean result = this.b.get(code);
        i.remove(code);
        return result;
    }

    public void show(LivingEntity sender, Location loc) {
        Bukkit.getScheduler().runTask(MamongUs.getPlugin(),() -> {
            while (true) {
            System.out.println("while탐지");
            if(!isSaving) {
                System.out.println("if 탐지");
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
                break;
                }
            }
        });
    }

    public void show(Player sender) {
        Bukkit.getScheduler().runTask(MamongUs.getPlugin(), () -> {
            try {
                while (true) {
                    if (!isSaving) {
                        int i = 0;
                        for (Integer x : blocks.keySet()) {
                            for (Integer y : blocks.get(x).keySet()) {
                                for (Integer z : blocks.get(x).get(y).keySet()) {
                                    sender.sendTitle("§a맵 생성중...", "§a" + i + "% 완료", 10, 10, 10);
                                }
                            }
                        }
                        break;
                    }
                }
            }catch (Exception e) {
                sender.sendTitle("§e§l경고", "§c맵 생성중 오류발생!", 10, 10, 10);
            }
        });
    }

    public void load(Location loc, boolean force, String direction, LivingEntity sender) {
        while (true) {
            if (!isSaving) {
                try {
                    Direction dir = Direction.toDirection(direction);
                    if (!force) {
                        loop: for (int i = 0;; i++) {
                            loopY: for (int y = 0; y <= 5; y++) {
                                switch (dir) {
                                    case SOUTH:
                                        if (isAir(loc)) {
                                            load(loc.add(0, Math.abs(pos1.getBlockY() - pos2.getBlockY()) * y, (Math.abs(pos1.getBlockZ() - pos2.getBlockZ()) + 1) * i));
                                            break loop;
                                        }
                                        break;
                                    case NORTH:
                                        if (isAir(loc)) {
                                            load(loc.add(0, Math.abs(pos1.getBlockY() - pos2.getBlockY()) * y, (-(Math.abs(pos1.getBlockZ() - pos2.getBlockZ()) + 1)) * i));
                                            break loop;
                                        }
                                        break;
                                    case EAST:
                                        if (isAir(loc)) {
                                            load(loc.add((Math.abs(pos1.getBlockX() - pos2.getBlockX()) + 1) * i, Math.abs(pos1.getBlockY() - pos2.getBlockY()) * y, 0));
                                            break loop;
                                        }
                                        break;
                                    case WEST:
                                        if (isAir(loc)) {
                                            load(loc.add((-(Math.abs(pos1.getBlockX() - pos2.getBlockX()) + 1)) * i, Math.abs(pos1.getBlockY() - pos2.getBlockY()) * y, 0));
                                            break loop;
                                        }
                                        break;
                                    case UP:
                                        if (isAir(loc)) {
                                            load(loc.add(0, (Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1) * i, 0));
                                            break loop;
                                        }
                                        break;
                                    case DOWN:
                                        if (isAir(loc)) {
                                            load(loc.add(0, -(Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1) * i, 0));
                                            break loop;
                                        }
                                        break;
                                }
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
                                load(loc.add(0, Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1, 0));
                                break;
                            case DOWN:
                                load(loc.add(0, -(Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1), 0));
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

    public void load(Location loc, boolean force, String direction) {
        while (true) {
            if (!isSaving) {
                try {
                    Direction dir = Direction.toDirection(direction);
                    if (!force) {
                        loop:
                        for (int i = 0; ; i++) {
                            switch (dir) {
                                case SOUTH:
                                    if (isAir(loc)) {
                                        load(loc.add(0, 0, (Math.abs(pos1.getBlockZ() - pos2.getBlockZ()) + 1) * i));
                                        break loop;
                                    }
                                    break;
                                case NORTH:
                                    if (isAir(loc)) {
                                        load(loc.add(0, 0, (-(Math.abs(pos1.getBlockZ() - pos2.getBlockZ()) + 1)) * i));
                                        break loop;
                                    }
                                    break;
                                case EAST:
                                    if (isAir(loc)) {
                                        load(loc.add((Math.abs(pos1.getBlockX() - pos2.getBlockX()) + 1) * i, 0, 0));
                                        break loop;
                                    }
                                    break;
                                case WEST:
                                    if (isAir(loc)) {
                                        load(loc.add((-(Math.abs(pos1.getBlockX() - pos2.getBlockX()) + 1)) * i, 0, 0));
                                        break loop;
                                    }
                                    break;
                                case UP:
                                    if (isAir(loc)) {
                                        load(loc.add((Math.abs(pos1.getBlockY() - pos2.getBlockY()) + 1) * i, 0, 0));
                                        break loop;
                                    }
                                    break;
                                case DOWN:
                                    if (isAir(loc)) {
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
                } catch (IllegalArgumentException ex) { }
                break;
            }
        }
    }
}
