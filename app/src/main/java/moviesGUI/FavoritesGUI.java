package moviesGUI;

import movies.Movie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoritesGUI {
    private static Set<Movie> movies;

    public FavoritesGUI(Set<Movie> movies) {
        this.movies = new HashSet<>(movies);
    }


    public Set<Movie> getMovies() {
        return movies;
    }

    public void addMovie(Movie movie){movies.add(movie);}
    public void deleteMovie(Movie movie){
        movies.removeIf(m -> m.getId() == movie.getId());

    }
}