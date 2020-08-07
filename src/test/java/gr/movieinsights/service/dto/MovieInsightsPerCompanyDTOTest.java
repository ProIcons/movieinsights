package gr.movieinsights.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class MovieInsightsPerCompanyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieInsightsPerCompanyDTO.class);
        MovieInsightsPerCompanyDTO movieInsightsPerCompanyDTO1 = new MovieInsightsPerCompanyDTO();
        movieInsightsPerCompanyDTO1.setId(1L);
        MovieInsightsPerCompanyDTO movieInsightsPerCompanyDTO2 = new MovieInsightsPerCompanyDTO();
        assertThat(movieInsightsPerCompanyDTO1).isNotEqualTo(movieInsightsPerCompanyDTO2);
        movieInsightsPerCompanyDTO2.setId(movieInsightsPerCompanyDTO1.getId());
        assertThat(movieInsightsPerCompanyDTO1).isEqualTo(movieInsightsPerCompanyDTO2);
        movieInsightsPerCompanyDTO2.setId(2L);
        assertThat(movieInsightsPerCompanyDTO1).isNotEqualTo(movieInsightsPerCompanyDTO2);
        movieInsightsPerCompanyDTO1.setId(null);
        assertThat(movieInsightsPerCompanyDTO1).isNotEqualTo(movieInsightsPerCompanyDTO2);
    }
}
