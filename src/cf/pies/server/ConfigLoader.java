package cf.pies.server;

import cf.pies.server.logger.Logger;
import cf.pies.server.server.Instance;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigLoader {
    public static void loadConfig(Main main) {
        try {
            JsonReader reader = new JsonReader(new FileReader("config.json"));
            JsonObject json = new Gson().fromJson(reader, JsonObject.class);

            Set<String> autoStart = new HashSet<>();
            if (json.has("auto-start")) {
                JsonArray startArray = json.getAsJsonArray("auto-start");
                for (JsonElement element : startArray) {
                    autoStart.add(element.getAsString());
                }
            }

            JsonArray serverArray = json.getAsJsonArray("servers");

            // TODO Delete instances that have been removed (confirm prompt?)
            for (JsonElement element : serverArray) {
                JsonObject serverObject = element.getAsJsonObject();
                if (!serverObject.has("name") || !serverObject.has("command")) {
                    Logger.log("Warning: JSON object found without name or command, you should review your config.json.");
                    continue;
                }
                String name = serverObject.get("name").getAsString();
                if (main.instances.stream().anyMatch(instance -> instance.name.equalsIgnoreCase(name))) {
                    Logger.log("Instance " + name + " already exists.");
                    continue;
                }
                List<String> commands = Arrays.asList(
                        serverObject.get("command").getAsString().split(" ")
                );
                Instance instance = new Instance(name, commands);
                if (serverObject.has("path")) {
                    instance.path(serverObject.get("path").getAsString());
                }
                if (autoStart.contains(instance.name)) {
                    instance.start();
                }
                main.instances.add(instance);
            }

            reader.close();
        } catch (Exception e) {
            Logger.error(e);
            Logger.log("Failed to read config.");
        }
    }
}
