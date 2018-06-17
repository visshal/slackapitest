package slacktest.cucumber.util;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class Util {
    public static JsonNode toJson(String s) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jnode = null;
        try {
            jnode = mapper.readTree(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jnode;
    }
}
