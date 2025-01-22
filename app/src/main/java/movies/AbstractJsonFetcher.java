package movies;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
public abstract class AbstractJsonFetcher {
    protected static void printMenuMovies(URL url){
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
                scanner.close();
                // Convertir la réponse JSON en objet MovieResponse
                MovieResponse movieResponse = LectureJSON.parseMovieList(informationString.toString());
                // Accéder à chaque film dans movieResponse et extraire les valeurs associées
                for (Movie movie : movieResponse.getResults()) {
                    System.out.println("Movie Title : " + movie.getTitle());
                    System.out.println("Movie Id : " + movie.getId());
                    System.out.println("Vote Average : " + movie.getVoteAverage());
                    System.out.println("Realease Date : "+ movie.getReleaseDate());
                    System.out.println("----------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}