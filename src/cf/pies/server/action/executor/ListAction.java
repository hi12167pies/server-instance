package cf.pies.server.action.executor;

import cf.pies.server.Main;
import cf.pies.server.action.Action;
import cf.pies.server.action.ActionExecutor;
import cf.pies.server.logger.Logger;
import cf.pies.server.server.Instance;

import java.util.List;

public class ListAction implements ActionExecutor {
    @Override
    public Action getAction() {
        return Action.LIST_INSTANCE;
    }

    @Override
    public void run(Main main, List<String> arguments) {
        for (int i = 0; i < main.instances.size(); i++) {
            Instance instance = main.instances.get(i);

            Logger.log(i + " - " + instance.name +
                    " (" + String.join(" ", instance.builder.command()) + ")"
                    + " (" + (instance.isAvailable() ? "Active" : "Inactive") + ")");
        }
    }
}
