package cf.pies.server.cli;

import cf.pies.server.action.Action;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Console {
    public Scanner scanner = new Scanner(System.in);
    public String prompter = "> ";
    public boolean prompting = false;

    /**
     * Shows the prompt character for the user
     */
    public void prompt() {
        this.prompting = true;
        System.out.print(this.prompter);
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
        }

        return new ExecutableLine(action, arguments.subList(1, arguments.size()));
    }
}
