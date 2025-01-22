package moviesGUI;

import com.google.gson.*;
import javafx.collections.ObservableList;
import movies.LectureJSON;
import movies.Movie;
import movies.MovieResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JsonFetcherGUI {
    public static List<Movie> fetchMovies(URL url) throws IOException {
        System.out.println("Fetching movies from URL: " + url);
        String response = fetchResponseFromUrl(url);
        System.out.println("Response: " + response);
        List<Movie> movies = parseMovies(response);
        if (movies.isEmpty()) {
            System.out.println("No movies parsed from response.");
        } else {
            System.out.println("Parsed " + movies.size() + " movies.");
        }
        return movies;
    }

    protected static void printMenuMovies(URL url, ObservableList<String> movies) {
        try {
            String response = fetchResponseFromUrl(url); // Use the same method to fetch response

            // Parse and add movie information to the observable list
            Gson gson = new Gson();
            JsonObject root = gson.fromJson(response, JsonObject.class);
            JsonArray results = root.getAsJsonArray("results");

            for (JsonElement movieElement : results) {
                JsonObject movieObject = movieElement.getAsJsonObject();
                String title = movieObject.get("title").getAsString();
                String releaseDate = movieObject.get("release_date").getAsString();
                double voteAverage = movieObject.get("vote_average").getAsDouble();
                String movieInfo = title + " (" + releaseDate + ") - Note: " + voteAverage;
                movies.add(movieInfo);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Consider logging this exception or handling it in a user-friendly manner
        }
    }

    private static String fetchResponseFromUrl(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP Response Code: " + responseCode);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    // Utility method to parse JSON response into a list of Movie objects
    /*private static List<Movie> parseMovies(String jsonResponse) {
        Gson gson = new Gson();
        JsonObject root = gson.fromJson(jsonResponse, JsonObject.class);
        JsonArray results = root.getAsJsonArray("results");
        List<Movie> movies = new ArrayList<>();

        for (JsonElement movieElement : results) {
            Movie movie = gson.fromJson(movieElement, Movie.class);
            movies.add(movie);
        }

        return movies;
    }*/

    private static List<Movie> parseMovies(String jsonResponse) {
        Gson gson = new Gson();
        JsonObject root = gson.fromJson(jsonResponse, JsonObject.class);

        if (root.has("results")) {
            JsonArray results = root.getAsJsonArray("results");
            List<Movie> movies = new ArrayList<>();

            for (JsonElement movieElement : results) {
                Movie movie = gson.fromJson(movieElement, Movie.class);
                movies.add(movie);
            }

            return movies;
        } else {
            System.out.println("La clé 'results' n'existe pas dans le JSON.");
            return new ArrayList<>(); // Ou une autre action appropriée, selon votre logique
        }
    }




    protected static void printMenuMovies2(URL url, ObservableList<String> movies) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder informationString = new StringBuilder();
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        informationString.append(line);
                    }
                }

                // Traiter la réponse JSON avec Gson
                Gson gson = new Gson();
                JsonObject root = gson.fromJson(informationString.toString(), JsonObject.class);
                JsonArray results = root.getAsJsonArray("results");
                for (JsonElement movieElement : results) {
                    JsonObject movieObject = movieElement.getAsJsonObject();
                    String title = movieObject.get("title").getAsString();
                    String releaseDate = movieObject.get("release_date").getAsString();
                    double voteAverage = movieObject.get("vote_average").getAsDouble();
                    String movieInfo = title + " (" + releaseDate + ") - Note: " + voteAverage;
                    movies.add(movieInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


