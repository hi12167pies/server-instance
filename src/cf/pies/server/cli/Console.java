package cf.pies.server.cli;

import cf.pies.server.Main;
import cf.pies.server.action.Action;
import cf.pies.server.utils.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Console {
    private final Main main;

    public Scanner scanner = new Scanner(System.in);
    public boolean prompting = false;

    public Console(Main main) {
        this.main = main;
    }

    /**
     * Clears the prompt from the terminal
     */
    public void resetPrompt() {
        System.out.print("\r" + (StringUtil.repeat(" ", main.console.getPrompter().length())) + "\r");
    }

    /**
     * @return The string used to prompt the user
     */
    public String getPrompter() {
        if (main.connectedInstance != null) {
            return main.connectedInstance.name + " > ";
        }
        return "> ";
    }

    /**
     * Shows the prompt character for the user
     */
    public void prompt() {
        this.prompting = true;
        System.out.print(this.getPrompter());
    }

    /**
     * Parsed a string line into an {@link ExecutableLine}
     */
    public ExecutableLine parseLine(String line) {
        List<String> arguments = Arrays.asList(line.split(" "));

        if (arguments.isEmpty()) {
            return null;
        }

        Action action = null;
        String firstArgument = arguments.get(0).toLowerCase();

        switch (firstArgument) {
            case "echo":
                action = Action.ECHO;
                break;
            case "list":
                action = Action.LIST_INSTANCE;
                break;
            case "start":
                action = Action.START_INSTANCE;
                break;
            case "test":
                action = Action.TEST;
                break;
            case "out":
                action = Action.OUT_INSTANCE;
                break;
            case "stop":
                action = Action.STOP_INSTANCE;
                break;
            case "connect":
                action = Action.CONNECT_INSTANCE;
                break;
            case "send":
                action = Action.SEND_INSTANCE;
                break;
            case "exit":
                action = Action.EXIT;
                break;
            case "reloadconfig":
            case "rlconfig":
                action = Action.RELOAD_CONFIG;
                break;
        }

        return new ExecutableLine(action, arguments.subList(1, arguments.size()));
    }
}
