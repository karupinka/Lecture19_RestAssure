package convector;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class JsonConvector {
    public static String jsonToString(String pathToJson) {

        String json = null;

        try (InputStream inputStream = JsonConvector.class.getResourceAsStream(pathToJson)) {
            json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

}
