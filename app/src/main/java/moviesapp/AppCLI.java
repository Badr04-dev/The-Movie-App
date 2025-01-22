package moviesapp;

import movies.Favorites;
import movies.JsonFetcher;
import movies.JsonSearch;
import movies.MovieDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppCLI {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to the movies app !");
        boolean openApp=true;
        boolean skipQuestion=true;
        boolean returnToMainMenu=false;
        List<JsonSearch> listOfJsonSearch=new ArrayList<>();
        Favorites favorites=new Favorites(listOfJsonSearch);
        while(openApp){
            do{
                returnToMainMenu=false;
                System.out.println("Welcome to the Main Menu ");
                Scanner scanner2 = new Scanner(System.in);
                System.out.println("Do you want to : ");
                System.out.println("1. See our movie suggestions ?");
                System.out.println("2. Do your own research ?");
                System.out.println("3. Manage my favorites list ?");
                int homePageChoice=scanner2.nextInt();
                if(homePageChoice==2){
                    Scanner scanner3 = new Scanner(System.in);
                    System.out.print("Enter the name of the movie : ");
                    String search=scanner3.nextLine();
                    JsonSearch jsonSearch=new JsonSearch(search);
                    jsonSearch.printSearchResults();
                } else if (homePageChoice==3) {
                    returnToMainMenu=manageFavorites(favorites,new Scanner(System.in));
                } else {
                    System.out.println("Discover -- Popular movie -- Now Playing -- Upcoming -- Top Rated");
                    Scanner scanner = new Scanner(System.in);
                    String menuChoice = scanner.nextLine();
                    if (menuChoice.equalsIgnoreCase("popular movie")) {
                        JsonFetcher.printPopularMovies();
                    } else if (menuChoice.equalsIgnoreCase("now playing")) {
                        JsonFetcher.printNowPlayingMovies();
                    } else if (menuChoice.equalsIgnoreCase("top rated")) {
                        JsonFetcher.printTopRatedMovies();
                    } else if (menuChoice.equalsIgnoreCase("Upcoming")) {
                        JsonFetcher.printUpcomingMovies();
                    } else if (menuChoice.equalsIgnoreCase("discover")) {
                        JsonFetcher.printDiscoverMovies();
                    }
                }
            }while(returnToMainMenu);


            while(skipQuestion){
                Scanner sc2=new Scanner(System.in);
                System.out.println("Do you want more details on any of the featured films ? (yes or no)");
                String movieDetails=sc2.nextLine();
                if(movieDetails.equalsIgnoreCase("yes")){
                    Scanner sc3=new Scanner(System.in);
                    System.out.print("Give the id of the chosen film : ");
                    int chosenMovie=sc3.nextInt();
                    JsonSearch jsonSearch=new JsonSearch(chosenMovie);
                    jsonSearch.printMovieDetails();
                }
                else{
                    skipQuestion=false;
                }
                Scanner sc3=new Scanner(System.in);
                System.out.println("Do you want to manage your favorites list ? (yes or no)");
                if(sc3.nextLine().equalsIgnoreCase("yes")) manageFavorites(favorites,new Scanner(System.in));
            }
            skipQuestion=true;

            Scanner sc=new Scanner(System.in);
            System.out.println("Do you want to return to the main menu (1) or close the application (2) ?");
            int loopChoice=sc.nextInt();
            if(loopChoice==2) openApp=false;
        }

    }

    private static boolean manageFavorites(Favorites favorites, Scanner scanner) throws Exception {
        System.out.println("1. View favorites");
        System.out.println("2. Add a movie to favorites");
        System.out.println("3. Remove a movie from favorites");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                // Afficher les films favoris
                favorites.printMovies();
                break;
            case 2:
                // Ajouter un film aux favoris
                System.out.print("Enter the id of the movie to add: ");
                int idToAdd = scanner.nextInt();
                JsonSearch jsonSearchToAdd = new JsonSearch(idToAdd);
                try {
                    jsonSearchToAdd.printMovieDetails();
                    favorites.addMovie(jsonSearchToAdd);
                    System.out.println("Movie added to favorites.");
                } catch (Exception e) {
                    System.out.println("Error adding movie to favorites: " + e.getMessage());
                }
                break;
            case 3:
                // Supprimer un film des favoris
                System.out.print("Enter the id of the movie to remove: ");
                int idToRemove = scanner.nextInt();
                JsonSearch jsonSearchToRemove = new JsonSearch(idToRemove);
                favorites.deleteMovie(jsonSearchToRemove);
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
        }
        return true;
    }
}


