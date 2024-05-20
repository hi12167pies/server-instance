package cf.pies.server.server;

import cf.pies.server.exception.ProcessOfflineException;
import cf.pies.server.logger.Logger;

import java.io.*;
import java.util.List;

public class Instance {
    public final String name;

    public Instance(String name, List<String> commands) {
        this.name = name;
        builder.command(commands);
    }

    public Process process = null;
    public ProcessBuilder builder = new ProcessBuilder();

    public boolean isAvailable() {
        return this.process != null;
    }

    public void sendInput(String text) throws ProcessOfflineException, IOException {
        if (!this.isAvailable()) throw new ProcessOfflineException();
        this.process.getOutputStream().write(text.getBytes());
        this.process.getOutputStream().flush();
    }

    public void start() throws IOException {
        Logger.log("[" + this.name + "] started.");
        this.out = new ByteArrayOutputStream();
        this.process = builder.start();
    }

    public ByteArrayOutputStream out;

    public void loop() {
        if (!this.isAvailable()) return;

        if (!this.process.isAlive()) {
            Logger.log("[" + this.name + "] died.");
            this.process.destroy();
            this.process = null;
            return;
        }

        try {
            while (process.getInputStream().available() > 0) {
                this.out.write(process.getInputStream().read());
            }

            while (process.getErrorStream().available() > 0) {
                this.out.write(process.getErrorStream().read());
            }
        } catch (IOException e) {
            Logger.error(e);
        }
    }
}
