package plugin.tools.data;

import org.bukkit.Bukkit;
import plugin.environment.lobby.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class    RoomData {

    //변하는 방정보
    private int nowPlayer;
    private final List<UUID> playerList = new ArrayList<>();
    private final List<UUID> survivals = new ArrayList<>();
    private final List<UUID> imposters = new ArrayList<>();
    private boolean showTask;

    //영구적 방정보
    private final int maxPlayer;
    private final UUID owner;
    private final String code;
    private final int imposterCount;

    public RoomData(int maxPlayer, UUID owner, String code, int imposterCount) {
        this.maxPlayer = maxPlayer;
        this.owner = owner;
        this.code = code;
        this.imposterCount = imposterCount;
    }

    public boolean eject(UUID player) {
        if(imposters.contains(player)) {
            imposters.remove(player);
            return true;
        }
        survivals.remove(player);
        return false;
    }

    public RoomData join(UUID uuid) {
        playerList.add(uuid);
        nowPlayer += 1;
        if(nowPlayer > maxPlayer) quit(uuid);
        return this;
    }

    public void quit(UUID uuid) {
        playerList.remove(uuid);
        nowPlayer -= 1;
        if(nowPlayer <= 0) {
            delete();
        }
    }

    public void delete() {
        if(playerList.size() == 0 && nowPlayer == 0) {
            Room.data.remove(code);
        }
    }
}
