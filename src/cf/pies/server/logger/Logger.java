package cf.pies.server.logger;

import cf.pies.server.Main;
import cf.pies.server.utils.StringUtil;

public class Logger {
    private static final Main main = Main.get();

    public static void log(Object object) {
        if (main.console.prompting) {
            // Reset to start of line, clear prompt with spaces, reset to start of line
            System.out.print("\r" + (StringUtil.repeat(" ", main.console.prompter.length())) + "\r");
        }
        System.out.println(object);
        // Prompt again
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
