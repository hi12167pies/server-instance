package cf.pies.server.server;

import cf.pies.server.server.error.ProcessOfflineException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Instance {
    public final String name;

    public Instance(String name, List<String> commands) {
        this.name = name;
        builder.command(commands);
    }

    public Process process = null;
    public ProcessBuilder builder = new ProcessBuilder();

    // Output is all the data (logs/stdout) the process will return
    public List<String> output = new ArrayList<>();

    public boolean isEnabled() {
        return this.process != null;
    }

    public void sendInput(String text) throws ProcessOfflineException, IOException {
        if (!this.isEnabled()) throw new ProcessOfflineException();
        this.process.getOutputStream().write(text.getBytes());
    }

    public void start() throws IOException {
        this.process = builder.start();
    }

    public void loop() {

    }
}
