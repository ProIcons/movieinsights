package gr.movieinsights.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class MovieInsightsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieInsights.class);
        MovieInsights movieInsights1 = new MovieInsights();
        movieInsights1.setId(1L);
        MovieInsights movieInsights2 = new MovieInsights();
        movieInsights2.setId(movieInsights1.getId());
        assertThat(movieInsights1).isEqualTo(movieInsights2);
        movieInsights2.setId(2L);
        assertThat(movieInsights1).isNotEqualTo(movieInsights2);
        movieInsights1.setId(null);
        assertThat(movieInsights1).isNotEqualTo(movieInsights2);
    }
}
