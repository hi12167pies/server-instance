package cf.pies.server.action.executor.general;

import cf.pies.server.Main;
import cf.pies.server.action.Action;
import cf.pies.server.action.ActionExecutor;

import java.util.List;

public class ExitAction implements ActionExecutor {
    @Override
    public Action getAction() {
        return Action.EXIT;
    }

    @Override
    public void run(Main main, List<String> arguments) {
        System.exit(0);
    }
}
