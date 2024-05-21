package cf.pies.server.utils;

public class StringUtil {
    /**
     * Repeats the string n times
     * @param str The string to repeat
     * @param times The amount of times to repeat it
     * @return The string repeated n times.
     */
    public static String repeat(String str, int times) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; i++) {
            builder.append(str);
        }
        return builder.toString();
    }
}
