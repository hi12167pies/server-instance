package cf.pies.server.utils;

import cf.pies.server.Main;
import cf.pies.server.server.Instance;

public class ActionUtil {
    private static final Main main = Main.get();

    /**
     * Gets the instance that corresponds to the string id
     * @param stringId The string id of the instance
     * @return The instance if it exists, or null if it does not exist or there is an error
     */
    public static Instance getInstance(String stringId) {
        int id;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            for (Instance instance : main.instances) {
                if (instance.name.equalsIgnoreCase(stringId)) {
                    return instance;
                }
            }
            return null;
        }
        if (main.instances.size() < id) {
            return null;
        }
        return main.instances.get(id);
    }
}
