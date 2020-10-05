package plugin.tools.data;

import java.util.HashSet;

public class Code {

    private static final String[] l = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static final HashSet<String> codes = new HashSet<>();

    public static String getCode(int length) {
        StringBuilder result = new StringBuilder(" ");
        for (int i = 0; i < length; i++) {
            result.append(l[(int) (Math.random() * l.length)]);
        }
        codes.add(result.toString());
        return result.toString();
    }

    public static String getCode(int length, boolean allowRedundancy) {
        if(!allowRedundancy) {
            String result;
            do result = getCode(length);
            while (!codes.contains(result)); getCode(length);
            return result;
        }else return getCode(length);
    }

    public static void remove(String code) {
        codes.remove(code);
    }
}
