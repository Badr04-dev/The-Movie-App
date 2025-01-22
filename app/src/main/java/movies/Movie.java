package movies;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Movie {
    private boolean adult;
    private String backdrop_path;
    private List<Integer> genre_ids;
    private int id;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private String poster_path;
    private String release_date;
    private String title;
    private boolean video;
    private double vote_average;
    private int vote_count;
    private boolean favorite;

    public Movie(boolean adult, String backdrop_path, List<Integer> genre_ids, int id, String original_language, String original_title, String overview, double popularity, String poster_path, String release_date, String title, boolean video, double vote_average, int vote_count, boolean favorite) {
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.genre_ids = genre_ids;
        this.id = id;
        this.original_language = original_language;
        this.original_title = original_title;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.favorite = favorite;
    }
    public boolean getAdult(){return adult;}
    public boolean getVideo(){return video;}
    public boolean getFavorite(){return favorite;}
    public String getBackdropPath(){return backdrop_path;}
    public String getOriginalLanguage(){return original_language;}
    public String getOverview(){return overview;}
    public String getPosterPath(){return poster_path;}
    public String getReleaseDate(){return release_date;}
    public String getTitle(){return title;}
    public List<Integer> getGenreIds(){return genre_ids;}
    public int getId(){return id;}
    public int getVoteCount(){return vote_count;}
    public double getPopularity(){return popularity;}
    public double getVoteAverage(){return vote_average;}

    @Override
    public String toString() {
        return "Movie{" +
                "adult=" + adult +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", genre_ids=" + genre_ids +
                ", id=" + id +
                ", original_language='" + original_language + '\'' +
                ", original_title='" + original_title + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", poster_path='" + poster_path + '\'' +
                ", release_date='" + release_date + '\'' +
                ", title='" + title + '\'' +
                ", video=" + video +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                '}';
    }
}