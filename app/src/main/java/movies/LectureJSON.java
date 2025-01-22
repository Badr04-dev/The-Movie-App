package movies;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
public class LectureJSON {
    public static MovieResponse parseMovieList(String json){
        Gson gson=new Gson();
        return gson.fromJson(json, MovieResponse.class);
    }
    public static Movie parseMovie(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Movie.class);
    }
    public static JsonObject parseGenre(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, JsonObject.class);
    }
}


