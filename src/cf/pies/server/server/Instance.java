package cf.pies.server.server;

import cf.pies.server.exception.ProcessOfflineException;

import java.io.IOException;
import java.util.List;

public class Instance {
    public final String name;

    public Instance(String name, List<String> commands) {
        this.name = name;
        builder.command(commands);
    }

    public void log(String data) {
        System.out.println("[" + this.name + "] has started.");
    }

    public Process process = null;
    public ProcessBuilder builder = new ProcessBuilder();

    // Output is all the data (logs/stdout) the process will return
    public byte[] output = new byte[]{};

    public boolean isAvailable() {
        return this.process != null;
    }

    public void sendInput(String text) throws ProcessOfflineException, IOException {
        if (!this.isAvailable()) throw new ProcessOfflineException();
        this.process.getOutputStream().write(text.getBytes());
        this.process.getOutputStream().flush();
    }

    public void start() throws IOException {
        this.process = builder.start();
    }

    public void loop() {
        if (!this.isAvailable()) return;

        if (!this.process.isAlive()) {
            System.out.println("[" + this.name + "] died.");
            this.process.destroy();
            this.process = null;
            return;
        }

        try {
            System.out.println(this.process.getInputStream().available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
