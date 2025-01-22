package movies;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MovieDetails {
    public static void printMovieDetails(URL url) throws Exception {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }

                Movie movie = LectureJSON.parseMovie(informationString.toString());

                System.out.println("Movie Title : " + movie.getTitle());
                System.out.println("Vote Average : " + movie.getVoteAverage());
                System.out.println("Realease Date : "+ movie.getReleaseDate());
                System.out.println("Genre : "+ GenreParser.getGenreNames(url));
                System.out.println("Popularity : "+ movie.getPopularity());
                System.out.println("Overview : "+ movie.getOverview());
                System.out.println("Original Language : "+ movie.getOriginalLanguage());
                System.out.println("Id : "+ movie.getId());
                System.out.println("----------------------------");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}