package cf.pies.server;

import cf.pies.server.action.Action;
import cf.pies.server.action.ActionExecutor;
import cf.pies.server.action.executor.EchoAction;
import cf.pies.server.cli.Console;
import cf.pies.server.cli.ConsoleThread;
import cf.pies.server.exception.ActionNotExistException;
import cf.pies.server.server.Instance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        new Main().start();
    }

    // ---


    public List<Instance> instances = new ArrayList<>();
    public HashMap<Action, ActionExecutor> executorMap = new HashMap<>();

    public void registerAction(ActionExecutor executor) {
        this.executorMap.put(executor.getAction(), executor);
    }

    public void executeAction(Action action, List<String> arguments) throws ActionNotExistException {
        if (!this.executorMap.containsKey(action)) {
            throw new ActionNotExistException();
        }
        this.executorMap.get(action).run(this, arguments);
    }

    public void start() {
        // Testing - Add example instance
        instances.add(new Instance("Java Version", Arrays.asList("java", "-version")));

        // Add actions
        this.registerAction(new EchoAction());

        ConsoleThread consoleThread = new ConsoleThread(this);
        consoleThread.start();
    }

    public void loop() {
        // Info loop
        for (Instance instance : instances) {
            instance.loop();
        }
    }
}