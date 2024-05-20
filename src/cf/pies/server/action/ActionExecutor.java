package cf.pies.server.action;

import cf.pies.server.Main;

import java.util.List;

public interface ActionExecutor {
    Action getAction();
    void run(Main main, List<String> arguments);
}
