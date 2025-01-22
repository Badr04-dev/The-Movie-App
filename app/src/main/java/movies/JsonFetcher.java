package movies;

import com.google.gson.Gson;
import java.net.URL;

public class JsonFetcher extends AbstractJsonFetcher{
    public static void printNowPlayingMovies() throws Exception {
        AbstractJsonFetcher.printMenuMovies(new URL("https://api.themoviedb.org/3/movie/now_playing?api_key=..."));
    }

    public static void printPopularMovies() throws Exception {
        AbstractJsonFetcher.printMenuMovies(new URL("https://api.themoviedb.org/3/movie/popular?api_key=..."));
    }

    public static void printTopRatedMovies() throws Exception {
        AbstractJsonFetcher.printMenuMovies(new URL("https://api.themoviedb.org/3/movie/top_rated?api_key=..."));
    }

    public static void printUpcomingMovies() throws Exception{
        AbstractJsonFetcher.printMenuMovies(new URL("https://api.themoviedb.org/3/movie/upcoming?api_key=..."));
    }

    public static void printDiscoverMovies() throws Exception{
        AbstractJsonFetcher.printMenuMovies(new URL("https://api.themoviedb.org/3/discover/movie?api_key=..."));
    }
}

