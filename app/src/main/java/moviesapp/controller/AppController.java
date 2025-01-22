package moviesapp.controller;

import com.google.gson.*;
import com.sun.javafx.menu.MenuItemBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import movies.Favorites;
import movies.GenreParser;
import movies.JsonSearch;
import movies.Movie;
import moviesGUI.FavoritesGUI;
import moviesGUI.JsonFetcherGUI;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import javafx.scene.shape.Rectangle;
import java.io.BufferedReader;
import java.awt.Desktop;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.net.URI;
import java.net.URISyntaxException;





public class AppController implements Initializable {

    @FXML
    private Button helloWorldButton;
    @FXML private Button goodByeWorldButton;
    @FXML private Label label;
    private List<JsonSearch> listOfJsonSearch=new ArrayList<>();
    private Favorites favorites=new Favorites(listOfJsonSearch);
    @FXML
    private ComboBox<String> genreComboBox;
    @FXML
    private ComboBox<String> mainMenu;
    @FXML
    private ComboBox<String> startYearComboBox;
    @FXML
    private ComboBox<String> endYearComboBox;
    @FXML
    private Slider ratingSlider;
    @FXML
    private TableColumn<Movie, String> genreColumn;
    @FXML
    private TableColumn<Movie, String> genre;
    @FXML
    private TableColumn<Movie, String> id;
    @FXML
    private ListView<Movie> favoritesListView;
    private FavoritesGUI favorite;
    private ObservableList<JsonSearch> favoritesList;
    @FXML
    private Label movieDetailsLabel;
    private ObservableList<Movie> favoriteList;
    @FXML
    private VBox centerContent;
    private ObservableList<Movie> moviesInCenter = FXCollections.observableArrayList();
    @FXML
    private TextField searchField;

    @FXML
    private ListView<Movie> movieListView;

    //@Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        genreComboBox.setItems(FXCollections.observableArrayList("Action", "Comedy", "Drama","Adventure","Animation","Crime","Documentary","Family","Fantasy","History","Horror","Music","Mystery","Romance","Science Fiction","TV Movie","Thriller","War","Western"));
        mainMenu.setItems(FXCollections.observableArrayList("Discover","Popular","Now Playing","Upcoming","Top Rated"));
        startYearComboBox.setItems(FXCollections.observableArrayList(getYears(1988, 2024)));
        endYearComboBox.setItems(FXCollections.observableArrayList(getYears(1988, 2024)));
        this.favorite = new FavoritesGUI(Set.of());
        this.favoriteList = FXCollections.observableArrayList();
        //favoritesListView.setItems(favoriteList);
        //showFavoriteMovieDetails();
        showMainMenu();
        movieListView.setCellFactory(new Callback<ListView<Movie>, ListCell<Movie>>() {
            @Override
            public ListCell<Movie> call(ListView<Movie> param) {
                return new MovieCell();
            }
        });

        movieListView.setStyle("-fx-background-color: #222222; -fx-padding: 20px;");


    }

    public void showMainMenu(){
        try {
            List<Movie> movies = JsonFetcherGUI.fetchMovies(new URL("https://api.themoviedb.org/3/discover/movie?api_key=8c1f02622cdb942096de14f60750f28b"));

            movieListView.setItems(FXCollections.observableArrayList(movies));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSuggestionsMenu(String querry){
        try {
            List<Movie> movies = JsonFetcherGUI.fetchMovies(new URL("https://api.themoviedb.org/3/movie/"+querry+"?api_key=8c1f02622cdb942096de14f60750f28b"));

            movieListView.setItems(FXCollections.observableArrayList(movies));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MovieCell extends ListCell<Movie> {
        private Label titleLabel = new Label();
        private Label releaseYearLabel = new Label();
        private Label ratingLabel = new Label();
        private Label genreLabel = new Label();
        private Label popularityLabel = new Label();
        private Label originalLanguageLabel = new Label();
        private Label overViewLabel = new Label();
        private Label idLabel = new Label();

        private Button addToFavoritesButton =new Button();
        private Button seeTrailerButton =new Button();

        public MovieCell() {
            setGraphic(new ScrollPane() {{
                setContent(new Label());
                setFitToWidth(true);
            }});
        }
        @Override
        protected void updateItem(Movie item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                ImageView imageView = showMovieImage(item);
                Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
                clip.setArcWidth(20);
                clip.setArcHeight(20);

                imageView.setClip(clip);

                VBox textVBox = new VBox(10); // 5 est l'espacement vertical entre les éléments
                textVBox.setAlignment(Pos.CENTER_LEFT); // Aligner les éléments à gauche

                titleLabel.setText(item.getTitle());
                releaseYearLabel.setText("Release Year: " + item.getReleaseDate());
                ratingLabel.setText("Rating: " + item.getVoteAverage());
                try {
                    genreLabel.setText("Genre: " + getGenreName(item.getId()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                popularityLabel.setText("Popularity: " + item.getPopularity());
                originalLanguageLabel.setText("Original Language: " + item.getOriginalLanguage());
                overViewLabel.setText("OverView: " + item.getOverview());

                boolean isFavorite = favorite.getMovies().contains(item);

                if (isFavorite) {
                    addToFavoritesButton.setText("Remove from Favorites");
                    addToFavoritesButton.setOnAction(event -> removeFromFavorites(item));
                } else {
                    addToFavoritesButton.setText("Add to Favorites");
                    addToFavoritesButton.setOnAction(event -> addToFavorites(item));
                }
                seeTrailerButton.setText("See Trailer !");
                seeTrailerButton.setOnAction(event -> seeTrailer(item));

                HBox buttonsBox = new HBox(10); // Espacement de 10 pixels entre les boutons
                buttonsBox.getChildren().addAll(addToFavoritesButton, seeTrailerButton);

                // Ajouter les labels et le bouton à textVBox
                textVBox.getChildren().addAll(
                        titleLabel,
                        releaseYearLabel,
                        ratingLabel,
                        genreLabel,
                        popularityLabel,
                        originalLanguageLabel,
                        overViewLabel,
                        buttonsBox
                );

                HBox hbox = new HBox(15); // 10 est l'espacement horizontal entre les éléments
                hbox.setAlignment(Pos.CENTER_LEFT); // Aligner les éléments à gauche
                hbox.getChildren().addAll(imageView, textVBox);
                titleLabel.setStyle("-fx-text-fill: #D35400; -fx-font-size: 30px; "); // rouge
                releaseYearLabel.setStyle("-fx-text-fill: #FFFFFF;"); // Blanc
                ratingLabel.setStyle("-fx-text-fill: #FFFFFF;"); // Blanc
                genreLabel.setStyle("-fx-text-fill: #FFFFFF;"); // Blanc
                popularityLabel.setStyle("-fx-text-fill: #FFFFFF;"); // Blanc
                originalLanguageLabel.setStyle("-fx-text-fill: #FFFFFF;"); // Blanc
                overViewLabel.setStyle("-fx-text-fill: #FFFFFF;"); // Blanc
                overViewLabel.setWrapText(true);
                hbox.setMaxWidth(1040);
                hbox.setStyle("-fx-background-color: #1C2833 ; -fx-padding: 0.2px; -fx-overflow-x: wrap-text; -fx-background-radius: 10px; -fx-spacing: 20px;");
                hbox.setOnMouseEntered(event -> {
                    hbox.setStyle("-fx-background-color: #D35400;-fx-padding: 0.2px; -fx-overflow-x: wrap-text; -fx-background-radius: 10px; -fx-spacing: 20px;");
                    titleLabel.setStyle("-fx-text-fill: black; -fx-font-size: 30px; ");
                    addToFavoritesButton.setStyle("-fx-background-color: #2F4F4F;");
                    seeTrailerButton.setStyle("-fx-background-color: #2F4F4F;");
                });

                hbox.setOnMouseExited(event -> {
                    hbox.setStyle("-fx-background-color: #1C2833;-fx-padding: 0.2px; -fx-overflow-x: wrap-text; -fx-background-radius: 10px; -fx-spacing: 20px;");
                    titleLabel.setStyle("-fx-text-fill: #D35400; -fx-font-size: 30px;");
                    addToFavoritesButton.setStyle("-fx-background-color: red;");
                    seeTrailerButton.setStyle("-fx-background-color: red;");
                });

                addToFavoritesButton.setOnMouseEntered(event -> {
                    addToFavoritesButton.setStyle("-fx-background-color: #778899;");
                });

                seeTrailerButton.setOnMouseEntered(event -> {
                    seeTrailerButton.setStyle("-fx-background-color: #778899;");
                });

                addToFavoritesButton.setOnMouseExited(event -> {
                    addToFavoritesButton.setStyle("-fx-background-color: #2F4F4F;");
                });


                seeTrailerButton.setOnMouseExited(event -> {
                    seeTrailerButton.setStyle("-fx-background-color: #2F4F4F;");
                });
                setGraphic(hbox);
            }
        }

    }

    private ImageView showMovieImage(Movie movie){
        ImageView imageView = new ImageView();
        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        Image image = new Image(imageUrl);
        imageView.setImage(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(300);
        return imageView;
    }

    private void seeTrailer(Movie movie){
        try {
            // Lecture du fichier JSON
            JsonObject reader = fetchJsonObject(new URL("https://api.themoviedb.org/3/movie/"+movie.getId()+"/videos?api_key=8c1f02622cdb942096de14f60750f28b"));
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            // Récupération du tableau "results" contenant les informations sur les vidéos
            JsonArray resultsArray = jsonObject.getAsJsonArray("results");

            // Parcours du tableau pour extraire les liens des vidéos
            for (JsonElement element : resultsArray) {
                JsonObject videoObject = element.getAsJsonObject();
                String site = videoObject.get("site").getAsString();
                String key = videoObject.get("key").getAsString();
                String type = videoObject.get("type").getAsString();

                if ("YouTube".equals(site) && type.equals("Trailer")) {
                    String videoLink = "https://www.youtube.com/watch?v=" + key;
                    System.out.println("Lien de la vidéo : " + videoLink);
                    openInBrowser(videoLink);

                    copyToClipboard(videoLink);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openInBrowser(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private static JsonObject fetchJsonObject(URL url) throws IOException {
        // Ouvrir la connexion HTTP
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Lire la réponse
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        }

        // Fermer la connexion
        connection.disconnect();

        // Convertir la réponse en JsonObject
        Gson gson = new Gson();
        return gson.fromJson(response.toString(), JsonObject.class);
    }

    private List<String> getYears(int startYear, int endYear) {
        List<String> years = new ArrayList<>();
        for (int year = startYear; year <= endYear; year++) {
            years.add(String.valueOf(year));
        }
        return years;
    }

    @FXML
    private void displayHelloWorld() {
        label.setText("Hello World");
        helloWorldButton.setVisible(false);
        if (!goodByeWorldButton.isVisible())
            goodByeWorldButton.setVisible(true);
    }

    @FXML
    private void goodByeWorld() {
        label.setText("");
        goodByeWorldButton.setVisible(false);
        if (!helloWorldButton.isVisible())
            helloWorldButton.setVisible(true);
    }

    @FXML
    private void searchMovies(ActionEvent event) {
        String query = searchField.getText();
        try {
            List<Movie> searchResults = JsonFetcherGUI.fetchMovies(new URL("https://api.themoviedb.org/3/search/movie?query=" + JsonSearch.traiterChaine(query) + "&api_key=8c1f02622cdb942096de14f60750f28b"));
            movieListView.getItems().setAll(searchResults);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public String getGenreName(int id) throws Exception {
        List<String> genreNames = new ArrayList<>();
        List<String> names = GenreParser.getGenreNames(new URL("https://api.themoviedb.org/3/movie/"+id+"?api_key=8c1f02622cdb942096de14f60750f28b"));
        if (names != null) {
            genreNames.addAll(names);
        }
        return toString(genreNames);
    }

    public String toString(List<String> list){
        String string="";
        if(list.isEmpty()) return string;
        for(int i=0;i< list.size()-2;i++){
            string+=list.get(i)+",";
        }
        string+=list.get(list.size()-1);
        return string;
    }


    @FXML
    private void applyFilter(ActionEvent event) throws Exception {
        String selectedGenre = genreComboBox.getValue();
        Integer startYear = null;
        Integer endYear = null;

        String startYearValue = startYearComboBox.getValue();
        String endYearValue = endYearComboBox.getValue();
        if (startYearValue != null && !startYearValue.isEmpty()) {
            startYear = Integer.parseInt(startYearValue);
        }
        if (endYearValue != null && !endYearValue.isEmpty()) {
            endYear = Integer.parseInt(endYearValue);
        }

        double selectedRating = ratingSlider.getValue();

        // Utiliser un ensemble pour stocker les identifiants des films déjà ajoutés
        Set<Integer> addedMovieIds = new HashSet<>();

        // Appliquer le filtre aux films dans la table
        ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();

        for (Movie movie : movieListView.getItems()) {
            String releaseDate = movie.getReleaseDate();
            if (releaseDate != null && releaseDate.length() >= 4) {
                int movieYear = Integer.parseInt(releaseDate.substring(0, 4));
                String genreNames = getGenreName(movie.getId());
                if ((selectedGenre == null || selectedGenre.isEmpty() || (genreNames != null && genreNames.contains(selectedGenre)))
                        && ((startYear == null || movieYear >= startYear) && (endYear == null || movieYear <= endYear))
                        && movie.getVoteAverage() >= selectedRating
                        && !addedMovieIds.contains(movie.getId())) {
                    filteredMovies.add(movie);
                    addedMovieIds.add(movie.getId());
                }

            }
        }

        movieListView.setItems(filteredMovies);
    }

    @FXML
    private void suggestionsMenu()  {
        String selectedMenu = mainMenu.getValue();
        switch (selectedMenu) {
            case "Discover" -> showMainMenu();
            case "Top Rated" -> showSuggestionsMenu("top_rated");
            case "Popular" -> showSuggestionsMenu("popular");
            case "Now Playing" -> showSuggestionsMenu("now_playing");
            case "Upcoming" -> showSuggestionsMenu("upcoming");
        }
    }


    @FXML
    public void showFavorites() {
        Set<Movie> favoriteMovies = favorite.getMovies();
        if (!favoriteMovies.isEmpty()) {
            try {
                movieListView.setItems(FXCollections.observableArrayList(favoriteMovies));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            movieListView.setItems(null);
            Text placeholderText = new Text("Aucun film dans les favoris");
            placeholderText.getStyleClass().add("placeholder-text");
            movieListView.setPlaceholder(placeholderText);
        }

        movieListView.refresh();
    }


    @FXML
    public void addToFavorites(Movie movie) {
        if (movie != null) {
            if (!favorite.getMovies().contains(movie)) {
                favorite.addMovie(movie);
            } else {
                showAlert("Error", "Movie already exists in favorites.");
            }
        } else {
            showAlert("Error", "No movie selected.");
        }
        movieListView.refresh();
    }

    private void removeFromFavorites(Movie selectedMovie) {
        if (selectedMovie != null) {
            favorite.deleteMovie(selectedMovie);
        } else {
            showAlert("Error", "No movie selected.");
        }
        showFavorites();
        movieListView.refresh();
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void showFavoriteMovieDetails(){
        favoritesListView.setOnMouseClicked(event -> {
            Movie selectedMovie = favoritesListView.getSelectionModel().getSelectedItem();
            if (selectedMovie != null) {
                try {
                    List<Movie> movies = JsonFetcherGUI.fetchMovies(new URL("https://api.themoviedb.org/3/movie/"+selectedMovie.getId()+"?api_key=8c1f02622cdb942096de14f60750f28b"));

                    movieListView.setItems(FXCollections.observableArrayList(movies));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}