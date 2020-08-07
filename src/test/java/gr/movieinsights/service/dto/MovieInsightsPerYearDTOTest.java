package gr.movieinsights.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class MovieInsightsPerYearDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieInsightsPerYearDTO.class);
        MovieInsightsPerYearDTO movieInsightsPerYearDTO1 = new MovieInsightsPerYearDTO();
        movieInsightsPerYearDTO1.setId(1L);
        MovieInsightsPerYearDTO movieInsightsPerYearDTO2 = new MovieInsightsPerYearDTO();
        assertThat(movieInsightsPerYearDTO1).isNotEqualTo(movieInsightsPerYearDTO2);
        movieInsightsPerYearDTO2.setId(movieInsightsPerYearDTO1.getId());
        assertThat(movieInsightsPerYearDTO1).isEqualTo(movieInsightsPerYearDTO2);
        movieInsightsPerYearDTO2.setId(2L);
        assertThat(movieInsightsPerYearDTO1).isNotEqualTo(movieInsightsPerYearDTO2);
        movieInsightsPerYearDTO1.setId(null);
        assertThat(movieInsightsPerYearDTO1).isNotEqualTo(movieInsightsPerYearDTO2);
    }
}
