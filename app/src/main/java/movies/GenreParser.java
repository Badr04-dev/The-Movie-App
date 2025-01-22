package movies;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class GenreParser {
    public static String getJsonString(URL url) throws Exception{
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public static List<String> getGenreNames(URL url) throws Exception {
        String apiKeyJson = getJsonString(url);

        JsonObject apiKeyObject= LectureJSON.parseGenre(apiKeyJson);

        JsonArray objectsArray = apiKeyObject.getAsJsonArray("genres");

        List<String> namesList = new ArrayList<>();

        // Parcours de l'array d'objets
        for (int i = 0; i < objectsArray.size(); i++) {
            JsonObject object = objectsArray.get(i).getAsJsonObject();
            String name = object.get("name").getAsString();
            namesList.add(name);
        }

        return namesList;
    }

}
