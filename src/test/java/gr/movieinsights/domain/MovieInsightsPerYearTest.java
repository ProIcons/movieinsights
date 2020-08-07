package gr.movieinsights.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class MovieInsightsPerYearTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieInsightsPerYear.class);
        MovieInsightsPerYear movieInsightsPerYear1 = new MovieInsightsPerYear();
        movieInsightsPerYear1.setId(1L);
        MovieInsightsPerYear movieInsightsPerYear2 = new MovieInsightsPerYear();
        movieInsightsPerYear2.setId(movieInsightsPerYear1.getId());
        assertThat(movieInsightsPerYear1).isEqualTo(movieInsightsPerYear2);
        movieInsightsPerYear2.setId(2L);
        assertThat(movieInsightsPerYear1).isNotEqualTo(movieInsightsPerYear2);
        movieInsightsPerYear1.setId(null);
        assertThat(movieInsightsPerYear1).isNotEqualTo(movieInsightsPerYear2);
    }
}
