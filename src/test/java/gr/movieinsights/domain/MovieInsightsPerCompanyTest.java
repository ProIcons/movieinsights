package gr.movieinsights.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class MovieInsightsPerCompanyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieInsightsPerCompany.class);
        MovieInsightsPerCompany movieInsightsPerCompany1 = new MovieInsightsPerCompany();
        movieInsightsPerCompany1.setId(1L);
        MovieInsightsPerCompany movieInsightsPerCompany2 = new MovieInsightsPerCompany();
        movieInsightsPerCompany2.setId(movieInsightsPerCompany1.getId());
        assertThat(movieInsightsPerCompany1).isEqualTo(movieInsightsPerCompany2);
        movieInsightsPerCompany2.setId(2L);
        assertThat(movieInsightsPerCompany1).isNotEqualTo(movieInsightsPerCompany2);
        movieInsightsPerCompany1.setId(null);
        assertThat(movieInsightsPerCompany1).isNotEqualTo(movieInsightsPerCompany2);
    }
}
