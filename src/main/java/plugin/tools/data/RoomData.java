package plugin.tools.data;

import plugin.environment.lobby.Room;
import plugin.tools.SectionSetter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoomData {

    //변하는 방정보
    private int nowPlayer;
    private final List<UUID> playerList = new ArrayList<>();
    private final List<UUID> survivals = new ArrayList<>();
    private final List<UUID> imposters = new ArrayList<>();
    private boolean showTask;

    private State state;

    //영구적 방정보
    private final int maxPlayer;
    private final UUID owner;
    private final String code;
    private final int imposterCount;
    private final int minPlayer;
    private final SectionData data;

    public enum State {
        Empty,
        Waiting,
        Playing
    }

    public RoomData(GameType type, UUID owner, String code, SectionData data) {
        this.maxPlayer = type.getMaxPlayer();
        this.owner = owner;
        this.code = code;
        this.imposterCount = type.getImposterCount();
        this.data = data;
        state = State.Waiting;
        minPlayer = type.getMinPlayer();
        int[] ints = data.load(data.getPos1(), false, SectionSetter.dir.get(type));
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
