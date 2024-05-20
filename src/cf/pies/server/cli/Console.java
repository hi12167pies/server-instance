package cf.pies.server.cli;

import cf.pies.server.common.Action;
import cf.pies.server.common.ExecutableLine;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Console {
    private final Scanner scanner = new Scanner(System.in);

    public String readLine() {
        System.out.print("$ ");
        return scanner.nextLine();
    }

    public ExecutableLine readParsedLine() {
        String line = this.readLine();
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
        }

        return new ExecutableLine(action, arguments.subList(1, arguments.size()));
    }

    public void log(Object object) {
        System.out.println(object);
    }
}
