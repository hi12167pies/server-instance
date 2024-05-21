package cf.pies.server.action.executor;

import cf.pies.server.Main;
import cf.pies.server.action.Action;
import cf.pies.server.action.ActionExecutor;
import cf.pies.server.logger.Logger;
import cf.pies.server.server.Instance;
import cf.pies.server.utils.ActionUtil;

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
        Instance instance = ActionUtil.getInstance(arguments.get(0));
        if (instance == null) {
            Logger.log(Logger.INSTANCE_NOT_FOUND);
            return;
        }
        try {
            instance.start();
        } catch (IOException e) {
            Logger.error(e);
            Logger.log("Failed to start instance.");
        }
    }
}
