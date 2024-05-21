package cf.pies.server.logger;

import cf.pies.server.Main;
import cf.pies.server.utils.StringUtil;

public class Logger {
    private static final Main main = Main.get();

    public static void log(Object object) {
        if (main.console.prompting) {
            main.console.resetPrompt();
        }
        System.out.println(object);
        if (main.console.prompting) {
            main.console.prompt();
        }
    }

    public static void logChar(char ch) {
        if (main.console.prompting) {
            main.console.resetPrompt();
        }
        System.out.print(ch);
        if (ch == '\n') {
            main.console.prompting = true;
            main.console.prompt();
        } else {
            main.console.prompting = false;
        }
    }

    public static void error(Exception e) {
        e.printStackTrace();
    }

    // Common strings
    public static String INSTANCE_NOT_FOUND = "Instance not found";
    public static String INSTANCE_NOT_AVAILABLE = "Instance not available";
}
