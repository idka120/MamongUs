package plugin.tools.data;

import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public enum GameType {
    OneImposter(4, 10, 1),
    TwoImposter(7, 10, 2),
    ThreeImposter(9, 10,3 ),
    Untimate(10, 13,3),
    Special(10, 20, 2);

    private final int minPlayer;
    private final int maxPlayer;
    private final int imposter;

    GameType(int minPlayer, int maxPlayer, int imposter) {
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
        this.imposter = imposter;
    }

    public int getMinPlayer() {
        return minPlayer;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public int getImposterCount() {
        return imposter;
    }

    public static GameType parseGameType(String type) {
        switch (type) {
            case "oneImposter" : return GameType.OneImposter;
            case "twoImposter" : return GameType.TwoImposter;
            case "threeImposter" : return GameType.ThreeImposter;
            case "special" : return GameType.Special;
            case "untimate" : return GameType.Untimate;
        }
        throw new IllegalArgumentException("unknown gameType");
    }

    public static GameType parseGameType(int cmd) {
        switch (cmd) {
            case 1 : return GameType.OneImposter;
            case 2 : return GameType.TwoImposter;
            case 3 : return GameType.ThreeImposter;
            case 4 : return GameType.Untimate;
            case 5 : return GameType.Special;
        }
        throw new IllegalArgumentException("unknown gameType");
    }
}
