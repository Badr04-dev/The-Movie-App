package movies;
import java.util.List;
import java.util.List;
public class Favorites {
    private List<JsonSearch> movies;
    public Favorites(List<JsonSearch> movies){this.movies=movies;}
    public List<JsonSearch> getMovies() {
        return movies;
    }
    public void printMovies() throws Exception{
        if(movies.isEmpty()) System.out.println("You currently have no films in your list");
        for(JsonSearch movie:movies){
            movie.printMovieDetails();
        }
    }
    public void addMovie(JsonSearch movie){movies.add(movie);}
    public void deleteMovie(JsonSearch movie){
        movies.removeIf(jsonSearch -> movie.getId() == jsonSearch.getId());

    }
}


