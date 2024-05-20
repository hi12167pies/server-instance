package cf.pies.server.action.executor;

import cf.pies.server.Main;
import cf.pies.server.action.Action;
import cf.pies.server.action.ActionExecutor;

import java.io.IOException;
import java.util.List;

public class StartAction implements ActionExecutor {
    @Override
    public Action getAction() {
        return Action.START_INSTANCE;
    }

    @Override
    public void run(Main main, List<String> arguments) {
        if (arguments.isEmpty()) return;
        int instance = Integer.parseInt(arguments.get(0));
        try {
            main.instances.get(instance).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
