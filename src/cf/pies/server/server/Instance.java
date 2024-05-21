package cf.pies.server.server;

import cf.pies.server.Main;
import cf.pies.server.exception.ProcessOfflineException;
import cf.pies.server.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Instance {
    public final String name;

    public ByteArrayOutputStream out;

    public Process process = null;
    public ProcessBuilder builder = new ProcessBuilder();

    public Instance(String name, List<String> commands) {
        this.name = name;
        builder.command(commands);
    }

    public Instance path(String path) {
        this.builder.directory(new File(path));
        return this;
    }

    public boolean isAvailable() {
        return this.process != null;
    }

    public void start() throws IOException {
        Logger.log("[" + this.name + "] started.");
        this.out = new ByteArrayOutputStream();
        this.process = builder.start();
    }

    public void kill() {
        if (this.isConnected()) {
            Main.get().disconnectInstance();
        }
        Logger.log("[" + this.name + "] stopping.");
        this.process.destroy();
        this.process = null;
    }

    public void sendInput(String text) throws ProcessOfflineException, IOException {
        if (!this.isAvailable()) throw new ProcessOfflineException();
        this.process.getOutputStream().write(text.getBytes());
        this.process.getOutputStream().flush();
    }

    public boolean isConnected() {
        Instance connectedInstance = Main.get().connectedInstance;
        return connectedInstance == this;
    }

    public void loop() {
        if (!this.isAvailable()) return;

        // If the process is no longer alive, we should end and destroy it.
        if (!this.process.isAlive()) {
            Logger.log("[" + this.name + "] not alive.");
            this.kill();
            return;
        }

        try {
            // Read the input (aka the process stdout) and the error (aka the process stderr)
            // I have put then on the same stream, but using different ones could maybe be useful?
            while (process.getInputStream().available() > 0) {
                int data = process.getInputStream().read();
                if (this.isConnected()) {
                    Logger.logChar((char) data);
                }
                this.out.write(data);
            }

            while (process.getErrorStream().available() > 0) {
                int data = process.getErrorStream().read();
                if (this.isConnected()) {
                    Logger.logChar((char) data);
                }
                this.out.write(data);
            }
        } catch (IOException e) {
            Logger.error(e);
        }
    }
}
