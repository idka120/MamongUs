package plugin.tools.data;

public enum Direction {

    WEST("west", 270, 90),
    EAST("east", 90, 90),
    NORTH("north", 0, 90),
    SOUTH("south", 180, 90),
    UP("up", 0, 0),
    DOWN("down", 0, 180);   

    private final String direction;
    private final int yaw;
    private final int pitch;

    Direction(String dir, int yaw, int pitch) {
        this.direction = dir;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public String getDirection() {
        return direction;
    }

    public int getYaw() {
        return yaw;
    }

    public int getPitch() {
        return pitch;
    }

    public static Direction toDirection(String str) {
        switch (str) {
            case "west":
                return Direction.WEST;
            case "east":
                return Direction.EAST;
            case "north":
                return Direction.NORTH;
            case "south":
                return Direction.SOUTH;
            default:
                throw new IllegalArgumentException("Known direction");
        }
    }
}
