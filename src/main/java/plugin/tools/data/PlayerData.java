package plugin.tools.data;

import plugin.tools.data.role.Role;

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
