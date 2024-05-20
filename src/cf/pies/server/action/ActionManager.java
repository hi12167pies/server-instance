package cf.pies.server.action;

import cf.pies.server.Main;
import cf.pies.server.cli.ExecutableLine;
import cf.pies.server.exception.ActionNotExistException;

import java.util.HashMap;
import java.util.List;

public class ActionManager {
    private final Main main;
    public ActionManager(Main main) {
        this.main = main;
    }

    public HashMap<Action, ActionExecutor> executorMap = new HashMap<>();

    public void registerAction(ActionExecutor executor) {
        this.executorMap.put(executor.getAction(), executor);
    }

    public void executeAction(ExecutableLine line) throws ActionNotExistException {
        this.executeAction(line.action, line.arguments);
    }

    public void executeAction(Action action, List<String> arguments) throws ActionNotExistException {
        if (!this.executorMap.containsKey(action)) {
            throw new ActionNotExistException();
        }
        this.executorMap.get(action).run(main, arguments);
    }
}
