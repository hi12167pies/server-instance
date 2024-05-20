package cf.pies.server;

import cf.pies.server.action.Action;
import cf.pies.server.action.ActionExecutor;
import cf.pies.server.action.executor.EchoAction;
import cf.pies.server.action.executor.ListAction;
import cf.pies.server.action.executor.OutAction;
import cf.pies.server.action.executor.StartAction;
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
        // Testing - Add example instances
        instances.add(new Instance("Java-Version", Arrays.asList("java", "-version")));
        instances.add(new Instance("Node-Version", Arrays.asList("node", "--version")));

        // Add actions
        this.registerAction(new EchoAction());
        this.registerAction(new StartAction());
        this.registerAction(new ListAction());
        this.registerAction(new OutAction());

        ConsoleThread consoleThread = new ConsoleThread(this);
        consoleThread.start();

        while (consoleThread.isAlive()) {
            for (Instance instance : this.instances) {
                instance.loop();
            }
        }
    }

    public void loop() {
        // Info loop
        for (Instance instance : instances) {
            instance.loop();
        }
    }
}