package cf.pies.server.action.executor.general;

import cf.pies.server.ConfigLoader;
import cf.pies.server.Main;
import cf.pies.server.action.Action;
import cf.pies.server.action.ActionExecutor;
import cf.pies.server.logger.Logger;

import java.util.List;

public class ReloadConfigAction implements ActionExecutor {
    @Override
    public Action getAction() {
        return Action.RELOAD_CONFIG;
    }

    @Override
    public void run(Main main, List<String> arguments) {
        Logger.log("Loading config...");
        ConfigLoader.loadConfig(main);
        Logger.log("Finished loading config.");
    }
}
