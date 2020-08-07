package gr.movieinsights.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class MovieInsightsPerPersonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieInsightsPerPerson.class);
        MovieInsightsPerPerson movieInsightsPerPerson1 = new MovieInsightsPerPerson();
        movieInsightsPerPerson1.setId(1L);
        MovieInsightsPerPerson movieInsightsPerPerson2 = new MovieInsightsPerPerson();
        movieInsightsPerPerson2.setId(movieInsightsPerPerson1.getId());
        assertThat(movieInsightsPerPerson1).isEqualTo(movieInsightsPerPerson2);
        movieInsightsPerPerson2.setId(2L);
        assertThat(movieInsightsPerPerson1).isNotEqualTo(movieInsightsPerPerson2);
        movieInsightsPerPerson1.setId(null);
        assertThat(movieInsightsPerPerson1).isNotEqualTo(movieInsightsPerPerson2);
    }
}
