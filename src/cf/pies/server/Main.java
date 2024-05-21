package cf.pies.server;

import cf.pies.server.action.ActionManager;
import cf.pies.server.action.executor.*;
import cf.pies.server.action.executor.general.ExitAction;
import cf.pies.server.action.executor.general.ReloadConfigAction;
import cf.pies.server.action.executor.instance.*;
import cf.pies.server.cli.Console;
import cf.pies.server.cli.ConsoleThread;
import cf.pies.server.logger.Logger;
import cf.pies.server.server.Instance;

import java.util.ArrayList;
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
    public Instance connectedInstance = null;

    public Console console = new Console(this);
    public ConsoleThread consoleThread = new ConsoleThread(this, this.console);
    public ActionManager actionManager = new ActionManager(this);

    public void disconnectInstance() {
        this.connectedInstance = null;
        console.prompt();
    }

    public void start() {
        // Add shutdown hook to close processes
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (Instance instance : instances) {
                if (instance.isAvailable()) {
                    Logger.log("Shutting down " + instance.name);
                    instance.kill();
                }
            }
        }));

        ConfigLoader.loadConfig(this);

        // Register actions - Instance actions
        actionManager.registerAction(new StartAction());
        actionManager.registerAction(new ListAction());
        actionManager.registerAction(new OutAction());
        actionManager.registerAction(new StopAction());
        actionManager.registerAction(new SendAction());
        actionManager.registerAction(new ConnectAction());

        // Register actions - General actions
        actionManager.registerAction(new EchoAction());
        actionManager.registerAction(new ReloadConfigAction());
        actionManager.registerAction(new ExitAction());

        // Start console thread, that handles user input.
        consoleThread.start();

        // If the console thread dies, then the program is done.
        while (consoleThread.isAlive()) {
            for (Instance instance : this.instances) {
                instance.loop();
            }
        }
    }
}