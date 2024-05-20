package cf.pies.server.exception;

public class ActionNotExistException extends Exception {
    public ActionNotExistException() {
        super("Action does not exist.");
    }
}
