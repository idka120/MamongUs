package plugin.tools.data.role.types;

public enum SabotageType {
    BreakLight(true),
    BreakOxygen(true),
    BreakReactor(true),
    CloseDoor(false);

    SabotageType(boolean Share) {
    }
}
