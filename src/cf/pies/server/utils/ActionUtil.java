package cf.pies.server.utils;

import cf.pies.server.Main;
import cf.pies.server.server.Instance;

public class ActionUtil {
    private static final Main main = Main.get();

    public static Instance getInstance(String id) {
        int i;
        try {
            i = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return null;
        }
        if (main.instances.size() < i) {
            return null;
        }
        return main.instances.get(i);
    }
}
