package cf.pies.server.server.error;

public class ProcessOfflineException extends Exception {
    public ProcessOfflineException() {
        super("The server process is currently offline.");
    }
}
