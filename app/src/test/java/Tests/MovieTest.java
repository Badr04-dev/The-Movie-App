package Tests;

import movies.Movie;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MovieTest {

    @Test
    public void testMovieGetters() {
        // Arrange
        Movie movie = new Movie(
                true, // adult
                "/backdrop/path", // backdrop_path
                Arrays.asList(28, 12, 16), // genre_ids
                550, // id
                "English", // original_language
                "Original Title", // original_title
                "This is a test overview.", // overview
                8.3, // popularity
                "/poster/path", // poster_path
                "2024-01-30", // release_date
                "Test Movie", // title
                false, // video
                7.9, // vote_average
                200, // vote_count
                true // favorite
        );

        // Act & Assert
        assertThat(movie.getAdult()).isTrue();
        assertThat(movie.getBackdropPath()).isEqualTo("/backdrop/path");
        assertThat(movie.getGenreIds()).containsExactly(28, 12, 16);
        assertThat(movie.getId()).isEqualTo(550);
        assertThat(movie.getOriginalLanguage()).isEqualTo("English");
        assertThat(movie.getOverview()).isEqualTo("This is a test overview.");
        assertThat(movie.getPopularity()).isEqualTo(8.3);
        assertThat(movie.getPosterPath()).isEqualTo("/poster/path");
        assertThat(movie.getReleaseDate()).isEqualTo("2024-01-30");
        assertThat(movie.getTitle()).isEqualTo("Test Movie");
        assertThat(movie.getVideo()).isFalse();
        assertThat(movie.getVoteAverage()).isEqualTo(7.9);
        assertThat(movie.getVoteCount()).isEqualTo(200);
        assertThat(movie.getFavorite()).isTrue();
    }
}
