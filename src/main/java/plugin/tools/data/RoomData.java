package plugin.tools.data;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import plugin.environment.lobby.Room;
import plugin.tools.SectionSetter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoomData {

    //변하는 방정보
    private int nowPlayer;
    private final List<Player> playerList = new ArrayList<>();
    private final List<Player> survivals = new ArrayList<>();
    private final List<Player> imposters = new ArrayList<>();
    private boolean showTask;

    private State state;

    //영구적 방정보
    private final int maxPlayer;
    private final Player owner;
    private final String code;
    private final int imposterCount;
    private final int minPlayer;

    public enum State {
        Empty,
        Waiting,
        Playing
    }

    public RoomData(GameType type, Player owner, String code) {
        this.maxPlayer = type.getMaxPlayer();
        this.owner = owner;
        this.code = code;
        this.imposterCount = type.getImposterCount();
        state = State.Waiting;
        minPlayer = type.getMinPlayer();
    }

    public boolean eject(Player player) {
        if(imposters.contains(player)) {
            imposters.remove(player);
            return true;
        }
        survivals.remove(player);
        return false;
    }

    public RoomData join(Player p) {
        playerList.add(p);
        nowPlayer += 1;
        if(nowPlayer > maxPlayer) quit(p);
        return this;
    }

    public void quit(Player p) {
        playerList.remove(p);
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
class RoomEvent implements Listener {

}
