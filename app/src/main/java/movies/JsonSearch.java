package movies;

import java.net.URL;

public class JsonSearch extends AbstractJsonFetcher{
    private String querry;
    private int id;
    public JsonSearch(String querry){
        this.querry=querry;
    }

    public JsonSearch(int id){
        this.id=id;
    }

    public String getQuerry() {
        return querry;
    }

    public int getId() {
        return id;
    }

    public static String traiterChaine(String chaine) {
        StringBuilder chaineModifiee = new StringBuilder();

        for (int i = 0; i < chaine.length(); i++) {
            char caractere = chaine.charAt(i);

            if (caractere == ' ') {
                chaineModifiee.append("%20");
            } else if (caractere == '\'') {
                chaineModifiee.append("%27");
            } else {
                chaineModifiee.append(caractere);
            }
        }

        return chaineModifiee.toString();
    }

    public void printSearchResults() throws Exception{
        String newQuerry=traiterChaine(querry);
        AbstractJsonFetcher.printMenuMovies(new URL("https://api.themoviedb.org/3/search/movie?query="+newQuerry+"&api_key=8c1f02622cdb942096de14f60750f28b"));
    }

    public void printMovieDetails() throws Exception{
        MovieDetails.printMovieDetails(new URL("https://api.themoviedb.org/3/movie/"+id+"?language=en-US&api_key=8c1f02622cdb942096de14f60750f28b"));
    }
}