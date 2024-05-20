package cf.pies.server;

import cf.pies.server.cli.Console;
import cf.pies.server.common.ExecutableLine;
import cf.pies.server.server.Instance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        new Main().start();
    }

    // ---


    public List<Instance> instances = new ArrayList<>();
    public Console console = new Console();

    public void start() {
        instances.add(new Instance("Java Version", Arrays.asList("java", "-version")));
        while (true) {
            this.loop();
        }
    }

    public void loop() {
        // Info loop
        for (Instance instance : instances) {
            instance.loop();
        }

        // This can be swapped out later, for a web panel or something, if needed.
        ExecutableLine line = console.readParsedLine();
        if (line == null) return;
        if (line.action == null) {
            console.log("Unknown action.");
            return;zz
        }

        // Handle actions
        switch (line.action) {
            case ECHO:
                console.log(String.join(" ", line.arguments));
                break;
            case LIST_INSTANCE:
                if (instances.isEmpty()) {
                    console.log("There are currently no instances.");
                    return;
                }
                for (Instance instance : instances) {
                    System.out.println(instance.name + " - (" + String.join(" ", instance.builder.command()) + ") - " + (instance.isEnabled() ? "Online" : "Offline"));
                }
                break;
        }
    }
}