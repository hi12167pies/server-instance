package cf.pies.server.action.executor;

import cf.pies.server.Main;
import cf.pies.server.action.Action;
import cf.pies.server.action.ActionExecutor;
import cf.pies.server.server.Instance;

import java.util.List;

public class OutAction implements ActionExecutor {
    @Override
    public Action getAction() {
        return Action.OUT_INSTANCE;
    }

    @Override
    public void run(Main main, List<String> arguments) {
        if (arguments.isEmpty()) return;
        int instanceId = Integer.parseInt(arguments.get(0));

        Instance instance = main.instances.get(instanceId);
        System.out.println(instance.out.toString());
    }
}
