package cf.pies.server.cli;

import cf.pies.server.Main;
import cf.pies.server.exception.ActionNotExistException;
import cf.pies.server.exception.ProcessOfflineException;
import cf.pies.server.logger.Logger;

import java.io.IOException;

public class ConsoleThread extends Thread implements Runnable {
    public final Console console;
    public final Main main;

    public ConsoleThread(Main main, Console console) {
        this.main = main;
        this.console = console;
    }


    @Override
    public void run() {
        console.prompt();

        while (console.scanner.hasNextLine()) {
            String line = console.scanner.nextLine();

            if (main.connectedInstance != null) {
                if (line.isEmpty()) {
                    console.prompt();
                    continue;
                }
                if (line.equalsIgnoreCase("exit")) {
                    main.disconnectInstance();
                    continue;
                }
                try {
                    main.connectedInstance.sendInput(line + "\r\n");
                } catch (ProcessOfflineException | IOException e) {
                    Logger.error(e);
                    Logger.log("Failed to send input to process.");
                }
                continue;
            }

            ExecutableLine executable = console.parseLine(line);
            console.prompting = false;

            if (executable == null) {
                Logger.log("Failed to parse line.");
                console.prompt();
                continue;
            }

            if (executable.action == null) {
                console.prompt();
                continue;
            }

            try {
                main.actionManager.executeAction(executable);
            } catch (ActionNotExistException e) {
                Logger.error(e);
                Logger.log("Failed to run action. " + executable.action);
            }

            // print prompter
            console.prompt();
        }
    }
}
