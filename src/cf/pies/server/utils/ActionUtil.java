package cf.pies.server.utils;

import cf.pies.server.Main;
import cf.pies.server.server.Instance;
import com.sun.istack.internal.Nullable;

public class ActionUtil {
    private static final Main main = Main.get();

    /**
     * Gets the instance that corresponds to the string id
     * @param stringId The string id of the instance
     * @return The instance if it exists, or null if it does not exist or there is an error
     */
    public static @Nullable Instance getInstance(String stringId) {
        int id;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            return null;
        }
        if (main.instances.size() < id) {
            return null;
        }
        return main.instances.get(id);
    }
}
