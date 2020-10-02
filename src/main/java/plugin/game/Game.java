package plugin.game;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import plugin.main.ItemBuild;
import plugin.tools.LocationSetter;
import plugin.tools.data.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Game {

    private List<ArmorStand> armorStands = new ArrayList<>();
    public static HashMap<UUID, Color> color = new HashMap<>();

    private Color getRandomColor() {
        return new Color[] {
        } [(int) (Math.random() * 12 )];
    }

    public ArmorStand createAS(Location loc, Direction dir) {
        Location location = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        location.setPitch(0);
        location.setYaw(dir.getYaw())   ;
        LeatherArmorMeta meta = (LeatherArmorMeta) new ItemBuild(Material.LEATHER_BOOTS, " ", null).getItem().getItemMeta();
        ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStands.add(as);
        return as;
    }

    private void Introduce(int  i, Location location) {

        World w = location.getWorld();
        int x;
        int z;

        int x2;
        int z2;

        int y = (int) location.getY();

        Direction direction = Direction.toDirection(LocationSetter.direction);
        switch (direction) {
            case WEST:
                for (int j = 0; j < i; j++) {
                    if(j % 2 == 1) {
                        x = (int) (j/2.0 + 0.5);
                        z = (int) (j/2.0 + 0.5);
                        createAS(new Location(w, location.getX() + x, y + 1.2, location.getZ() + z), direction);
                    }else {
                        x2 = j/2;
                        z2 = -(j/2);
                        createAS(new Location(w, location.getX() + x2, y + 1.2, location.getZ() + z2), direction);
                    }
                }
                break;
            case EAST:
                for (int j = 0; j < i; j++) {
                    if(j % 2 == 1) {
                        x = (int) -(j/2.0 + 0.5);
                        z = (int) -(j/2.0 + 0.5);
                        createAS(new Location(w, location.getX() + x, y + 1.2, location.getZ() + z), direction);
                    }else {
                        x2 = -(j/2);
                        z2 = +(j/2);
                        createAS(new Location(w, location.getX() + x2, y + 1.2, location.getZ() + z2), direction);
                    }
                }
                break;
            case NORTH:
                for (int j = 0; j < i; j++) {
                    if(j % 2 == 1) {
                        x = (int) +(j/2.0 + 0.5);
                        z = (int) -(j/2.0 + 0.5);
                        createAS(new Location(w, location.getX() + x, y + 1.2, location.getZ() + z), direction);
                    }else {
                        x2 = (j/2);
                        z2 = (j/2);
                        createAS(new Location(w, location.getX() + x2, y + 1.2, location.getZ() + z2), direction);
                    }
                }
                break;
            case SOUTH:
                for (int j = 0; j < i; j++) {
                    if(j % 2 == 1) {
                        x = (int) -(j/2.0 + 0.5);
                        z = (int) +(j/2.0 + 0.5);
                        createAS(new Location(w, location.getX() + x, y + 1.2, location.getZ() + z), direction);
                    }else {
                        x2 = -(j/2);
                        z2 = -(j/2);
                        createAS(new Location(w, location.getX() + x2, y + 1.2, location.getZ() + z2), direction);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void onStart() {
        Location loc = LocationSetter.crew;
    }
}
