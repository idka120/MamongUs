package plugin.game;

public enum Direction {

    /**
     * 함수식: y = -x(x는 양수)와 y = x(x는 음수)
     */
    WEST("west", 270),
    /**
     * 함수식: y = -x(x는 음 수)와 y = x(x는 양수)
     */
    EAST("east", 90),
    /**
     * 함수식: y = -x(x는 음수)와 y = x(x는 음수)
     */
    NORTH("north", 0),
    /**
     * 함수식: y = -x(x는 양수)와 y = x(x는 양수)
     */
    SOUTH("south", 180);

    private final String direction;
    private final int yaw;

    Direction(String dir, int yaw) {
        this.direction = dir;
        this.yaw = yaw;
    }

    public String getDirection() {
        return direction;
    }

    public int getYaw() {
        return yaw;
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
