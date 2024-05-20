package cf.pies.server.action.executor;

import cf.pies.server.Main;
import cf.pies.server.action.Action;
import cf.pies.server.action.ActionExecutor;

import java.util.List;

public class EchoAction implements ActionExecutor {
    @Override
    public Action getAction() {
        return Action.ECHO;
    }

    @Override
    public void run(Main main, List<String> arguments) {
        System.out.println(String.join(" ", arguments));
    }
}