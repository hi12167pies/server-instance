package cf.pies.server.action.executor.instance;

import cf.pies.server.Main;
import cf.pies.server.action.Action;
import cf.pies.server.action.ActionExecutor;
import cf.pies.server.logger.Logger;
import cf.pies.server.server.Instance;
import cf.pies.server.utils.ActionUtil;

import java.util.List;

public class OutAction implements ActionExecutor {
    @Override
    public Action getAction() {
        return Action.OUT_INSTANCE;
    }

    @Override
    public void run(Main main, List<String> arguments) {
        if (arguments.isEmpty()) return;
        Instance instance = ActionUtil.getInstance(arguments.get(0));
        if (instance == null) {
            Logger.log(Logger.INSTANCE_NOT_FOUND);
            return;
        }
        if (instance.out == null) {
            Logger.log("Instance has no output.");
            return;
        }
        Logger.log(instance.out.toString());
    }
}
