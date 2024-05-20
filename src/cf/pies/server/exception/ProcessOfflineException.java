package cf.pies.server.exception;

public class ProcessOfflineException extends Exception {
    public ProcessOfflineException() {
        super("The server process is currently offline.");
    }
}
