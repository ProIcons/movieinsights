package gr.movieinsights.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class MovieInsightsPerCountryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieInsightsPerCountry.class);
        MovieInsightsPerCountry movieInsightsPerCountry1 = new MovieInsightsPerCountry();
        movieInsightsPerCountry1.setId(1L);
        MovieInsightsPerCountry movieInsightsPerCountry2 = new MovieInsightsPerCountry();
        movieInsightsPerCountry2.setId(movieInsightsPerCountry1.getId());
        assertThat(movieInsightsPerCountry1).isEqualTo(movieInsightsPerCountry2);
        movieInsightsPerCountry2.setId(2L);
        assertThat(movieInsightsPerCountry1).isNotEqualTo(movieInsightsPerCountry2);
        movieInsightsPerCountry1.setId(null);
        assertThat(movieInsightsPerCountry1).isNotEqualTo(movieInsightsPerCountry2);
    }
}
