package cf.pies.server.cli;

import cf.pies.server.Main;
import cf.pies.server.common.ExecutableLine;
import cf.pies.server.exception.ActionNotExistException;

public class ConsoleThread extends Thread implements Runnable {
    public Console console = new Console();
    public final Main main;

    public ConsoleThread(Main main) {
        this.main = main;
    }


    @Override
    public void run() {
        console.prompt();
        while (console.scanner.hasNextLine()) {
            String line = console.scanner.nextLine();
            ExecutableLine executable = console.parseLine(line);

            if (executable == null) {
                System.out.println("Failed to parse line.");
                return;
            }

            if (executable.action == null) {
                System.out.println("Action not found.");
                return;
            }

            try {
                main.executeAction(executable.action, executable.arguments);
            } catch (ActionNotExistException e) {
                System.out.println("Failed to run action. " + executable.action);
            }

            // print prompter
            console.prompt();
        }
    }
}
