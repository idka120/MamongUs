package plugin.environment.lobby;

import org.bukkit.Bukkit;

import java.util.UUID;

public class PrivateRoom {

    public static void join(UUID uuid, String code) {
        if(Room.codes.contains(code)) {

        }else {
            try {
                Bukkit.getPlayer(uuid);
            }catch (NullPointerException e) {
                System.out.println("불완전한 플레이어를 호출 하였습니다");
            }
        }
    }
}
