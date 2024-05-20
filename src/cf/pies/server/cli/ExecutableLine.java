package cf.pies.server.cli;

import cf.pies.server.action.Action;

import java.util.List;

public class ExecutableLine {
    public final Action action;
    public final List<String> arguments;

    public ExecutableLine(Action action, List<String> arguments) {
        this.action = action;
        this.arguments = arguments;
    }
}
