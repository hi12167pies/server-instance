package cf.pies.server.logger;

import cf.pies.server.Main;

public class Logger {
    private static final Main main = Main.get();

    public static void log(Object object) {
        System.out.println(object);
        if (main.console.prompting) {
            main.console.prompt();
        }
    }

    public static void error(Exception e) {
        e.printStackTrace();
    }

    // Common strings
    public static String INSTANCE_NOT_FOUND = "Instance not found";
}
