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
import java.util.List;

public class ConfigLoader {
    public static void loadConfig(Main main) {
        try {
            JsonReader reader = new JsonReader(new FileReader("config.json"));
            JsonObject json = new Gson().fromJson(reader, JsonObject.class);

            JsonArray serverArray = json.getAsJsonArray("servers");

            for (JsonElement element : serverArray) {
                JsonObject serverObject = element.getAsJsonObject();
                if (!serverObject.has("name") || !serverObject.has("command")) {
                    Logger.log("Warning: JSON object found without name or command, you should review your config.json.");
                    continue;
                }
                String name = serverObject.get("name").getAsString();
                List<String> commands = Arrays.asList(
                        serverObject.get("command").getAsString().split(" ")
                );
                Instance instance = new Instance(name, commands);
                if (serverObject.has("path")) {
                    instance.path(serverObject.get("path").getAsString());
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
