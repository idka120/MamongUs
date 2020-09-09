package plugin.main;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugin.game.Direction;
import plugin.game.Game;

public class TestCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("test")) {
            Player p = (Player) sender;

            Location location = p.getLocation();

            World w = location.getWorld();
            int x;
            int z;

            int x2;
            int z2;

            int y = (int) location.getY();

            int i = 10;
            Direction direction = Direction.WEST;
            switch (direction) {
                case WEST:
                    for (int j = 0; j < i; j++) {
                        if (j % 2 == 1) {
                            x = (int) (j / 2.0 + 0.5);
                            z = (int) (j / 2.0 + 0.5);
                            new Game().createAS(new Location(w, location.getX() + x, y + 0.2, location.getZ() + z), direction);
                        } else {
                            x2 = j / 2;
                            z2 = -(j / 2);
                            new Game().createAS(new Location(w, location.getX() + x2, y + 0.2, location.getZ() + z2), direction);
                        }
                    }
                    break;
                case EAST:
                    for (int j = 0; j < i; j++) {
                        if (j % 2 == 1) {
                            x = (int) -(j / 2.0 + 0.5);
                            z = (int) -(j / 2.0 + 0.5);
                            new Game().createAS(new Location(w, location.getX() + x, y + 0.2, location.getZ() + z), direction);
                        } else {
                            x2 = -(j / 2);
                            z2 = +(j / 2);
                            new Game().createAS(new Location(w, location.getX() + x2, y + 0.2, location.getZ() + z2), direction);
                        }
                    }
                    break;
                case NORTH:
                    for (int j = 0; j < i; j++) {
                        if (j % 2 == 1) {
                            x = (int) +(j / 2.0 + 0.5);
                            z = (int) -(j / 2.0 + 0.5);
                            new Game().createAS(new Location(w, location.getX() + x, y + 0.2, location.getZ() + z), direction);
                        } else {
                            x2 = (j / 2);
                            z2 = (j / 2);
                            new Game().createAS(new Location(w, location.getX() + x2, y + 0.2, location.getZ() + z2), direction);
                        }
                    }
                    break;
                case SOUTH:
                    for (int j = 0; j < i; j++) {
                        if (j % 2 == 1) {
                            x = (int) -(j / 2.0 + 0.5);
                            z = (int) +(j / 2.0 + 0.5);
                            new Game().createAS(new Location(w, location.getX() + x, y + 0.2, location.getZ() + z), direction);
                        } else {
                            x2 = -(j / 2);
                            z2 = -(j / 2);
                            new Game().createAS(new Location(w, location.getX() + x2, y + 0.2, location.getZ() + z2), direction);
                        }
                    }
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }else if(cmd.getName().equalsIgnoreCase("t")) {
            Player p = (Player) sender;

            Location location = p.getLocation();

            World w = location.getWorld();
            int x;
            int z;

            int x2;
            int z2;

            int y = (int) location.getY();

            int i = 10;
            Direction direction = Direction.WEST;
            switch (direction) {
                case EAST:
                    for (int j = 0; j < i; j++) {
                        if (j % 2 == 1) {
                            x = (int) (i / 2.0 + 0.5);
                            z = (int) (i / 2.0 + 0.5);
                            sender.sendMessage("x : " + (location.getX() + x) + ", y : " + (location.getY() + 1.2) + ", z : " + (location.getZ() + z));
                        } else {
                            x2 = i / 2;
                            z2 = -(i / 2);
                            sender.sendMessage("x : " + (location.getX() + x2) + ", y : " + (location.getY() + 1.2) + ", z : " + (location.getZ() + z2));
                        }
                    }
                    break;
                case WEST:
                    for (int j = 0; j < i; j++) {
                        if (j % 2 == 1) {
                            x = (int) -(i / 2.0 + 0.5);
                            z = (int) -(i / 2.0 + 0.5);
                            sender.sendMessage("x : " + (location.getX() + x) + ", y : " + (location.getY() + 1.2) + ", z : " + (location.getZ() + z));
                        } else {
                            x2 = -(i / 2);
                            z2 = +(i / 2);
                            sender.sendMessage("x : " + (location.getX() + x2) + ", y : " + (location.getY() + 1.2) + ", z : " + (location.getZ() + z2));
                        }
                    }
                    break;
                case SOUTH:
                    for (int j = 0; j < i; j++) {
                        if (j % 2 == 1) {
                            x = (int) +(i / 2.0 + 0.5);
                            z = (int) -(i / 2.0 + 0.5);
                            sender.sendMessage("x : " + (location.getX() + x) + ", y : " + (location.getY() + 1.2) + ", z : " + (location.getZ() + z));
                        } else {
                            x2 = (i / 2);
                            z2 = (i / 2);
                            sender.sendMessage("x : " + (location.getX() + x2) + ", y : " + (location.getY() + 1.2) + ", z : " + (location.getZ() + z2));
                        }
                    }
                    break;
                case NORTH:
                    for (int j = 0; j < i; j++) {
                        if (j % 2 == 1) {
                            x = (int) -(i / 2.0 + 0.5);
                            z = (int) +(i / 2.0 + 0.5);
                            sender.sendMessage("x : " + (location.getX() + x) + ", y : " + (location.getY() + 1.2) + ", z : " + (location.getZ() + z));
                        } else {
                            x2 = -(i / 2);
                            z2 = -(i / 2);
                            sender.sendMessage("x : " + (location.getX() + x2) + ", y : " + (location.getY() + 1.2) + ", z : " + (location.getZ() + z2));
                        }
                    }
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }else if(cmd.getName().equalsIgnoreCase("test2")) {

            int x;
            int z;

            int x2;
            int z2;

            Player p = (Player) sender;

            Location location = p.getLocation();
            int i = 10;

            for (int j = 0; j < i; j++) {
                if(j % 2 == 1) {
                    x = (int) (j/2.0 + 0.5);
                    z = (int) (j/2.0 + 0.5);
                    sender.sendMessage("x : " + (location.getX() + x) + ", y : " + (location.getY() + 1.2) + ", z : " + (location.getZ() + z));
                }else {
                    x2 = j/2;
                    z2 = -(j/2);
                    sender.sendMessage("x : " + (location.getX() + x2) + ", y : " + (location.getY() + 1.2) + ", z : " + (location.getZ() + z2));
                }
            }
        }
        return false;
    }
}
