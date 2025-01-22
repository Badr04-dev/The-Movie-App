package movies;

import java.util.List;

public class MovieResponse {
    private int page;
    private List<Movie> results;
    private int total_pages;
    private int total_results;

    public int getPage(){return page;}
    public int getTotalPages(){return total_pages;}
    public int getTotalResults(){return total_results;}

    public List<Movie> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "page=" + page +
                ", results=" + results +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}


