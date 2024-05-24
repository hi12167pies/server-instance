package cf.pies.server;

import cf.pies.server.logger.Logger;
import cf.pies.server.server.Instance;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

            JsonArray serverArray = json.getAsJsonArray("instances");

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
                List<String> commands = splitSpace(serverObject.get("command").getAsString());
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

    public static List<String> splitSpace(String str) {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("\"([^\"]*)\"|\\S+");
        Matcher matcher = pattern.matcher(str);

        // Print the parts
        while (matcher.find()) {
            String match = matcher.group();
            if (match.startsWith("\"")) {
                match = match.substring(1);
            }
            if (match.endsWith("\"")) {
                match = match.substring(0, match.length() - 1);
            }
            list.add(match);
        }

        return list;
    }
}
