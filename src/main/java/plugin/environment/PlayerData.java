package plugin.environment;

import plugin.main.Role;

public class PlayerData {

    private Role role;

    public PlayerData setRole(Role role) {
        this.role = role;
        return this;
    }

    public Role getRole() {
        return role;
    }
}
