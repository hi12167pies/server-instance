package cf.pies.server.action.executor.instance;

import cf.pies.server.Main;
import cf.pies.server.action.Action;
import cf.pies.server.action.ActionExecutor;
import cf.pies.server.exception.ProcessOfflineException;
import cf.pies.server.logger.Logger;
import cf.pies.server.server.Instance;
import cf.pies.server.utils.ActionUtil;

import java.io.IOException;
import java.util.List;

public class SendAction implements ActionExecutor {
    @Override
    public Action getAction() {
        return Action.SEND_INSTANCE;
    }

    @Override
    public void run(Main main, List<String> arguments) {
        if (arguments.isEmpty()) return;
        Instance instance = ActionUtil.getInstance(arguments.get(0));
        if (instance == null) {
            Logger.log(Logger.INSTANCE_NOT_FOUND);
            return;
        }
        if (!instance.isAvailable()) {
            Logger.log(Logger.INSTANCE_NOT_AVAILABLE);
            return;
        }
        try {
            String data = String.join(" ", arguments.subList(1, arguments.size()));
            instance.sendInput(data + "\n");
            Logger.log("Sent input: " + data);
        } catch (ProcessOfflineException | IOException e) {
            Logger.error(e);
            Logger.log("Failed to send input.");
        }
    }
}
