package cf.pies.server;

import cf.pies.server.action.Action;
import cf.pies.server.action.ActionExecutor;
import cf.pies.server.action.ActionManager;
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
    private static final Main main = new Main();
    public static Main get() {
        return main;
    }
    public static void main(String[] args) {
        main.start();
    }

    // ---

    public List<Instance> instances = new ArrayList<>();

    public Console console = new Console();
    public ConsoleThread consoleThread = new ConsoleThread(this, this.console);
    public ActionManager actionManager = new ActionManager(this);
    public void start() {
        // Testing - Add example instances
        instances.add(new Instance("Java-Version", Arrays.asList("java", "-version")));
        instances.add(new Instance("Node-Version", Arrays.asList("node", "--version")));

        // Add actions
        this.actionManager.registerAction(new EchoAction());
        this.actionManager.registerAction(new StartAction());
        this.actionManager.registerAction(new ListAction());
        this.actionManager.registerAction(new OutAction());

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