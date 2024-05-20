package cf.pies.server.common;

import cf.pies.server.action.Action;

import java.util.List;

public class ExecutableLine {
    public ExecutableLine(Action action, List<String> arguments) {
        this.action = action;
        this.arguments = arguments;
    }

    public final Action action;
    public final List<String> arguments;
}
