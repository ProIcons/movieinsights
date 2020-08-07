package gr.movieinsights.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class MovieInsightsPerGenreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieInsightsPerGenre.class);
        MovieInsightsPerGenre movieInsightsPerGenre1 = new MovieInsightsPerGenre();
        movieInsightsPerGenre1.setId(1L);
        MovieInsightsPerGenre movieInsightsPerGenre2 = new MovieInsightsPerGenre();
        movieInsightsPerGenre2.setId(movieInsightsPerGenre1.getId());
        assertThat(movieInsightsPerGenre1).isEqualTo(movieInsightsPerGenre2);
        movieInsightsPerGenre2.setId(2L);
        assertThat(movieInsightsPerGenre1).isNotEqualTo(movieInsightsPerGenre2);
        movieInsightsPerGenre1.setId(null);
        assertThat(movieInsightsPerGenre1).isNotEqualTo(movieInsightsPerGenre2);
    }
}
