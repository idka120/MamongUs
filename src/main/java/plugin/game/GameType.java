package plugin.game;

public enum GameType {
    OneImposter,
    TwoImposter,
    ThreeImposter,
    Untimate,
    Special;

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
}
