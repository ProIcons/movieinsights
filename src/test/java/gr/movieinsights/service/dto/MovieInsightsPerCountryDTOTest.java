package gr.movieinsights.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class MovieInsightsPerCountryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieInsightsPerCountryDTO.class);
        MovieInsightsPerCountryDTO movieInsightsPerCountryDTO1 = new MovieInsightsPerCountryDTO();
        movieInsightsPerCountryDTO1.setId(1L);
        MovieInsightsPerCountryDTO movieInsightsPerCountryDTO2 = new MovieInsightsPerCountryDTO();
        assertThat(movieInsightsPerCountryDTO1).isNotEqualTo(movieInsightsPerCountryDTO2);
        movieInsightsPerCountryDTO2.setId(movieInsightsPerCountryDTO1.getId());
        assertThat(movieInsightsPerCountryDTO1).isEqualTo(movieInsightsPerCountryDTO2);
        movieInsightsPerCountryDTO2.setId(2L);
        assertThat(movieInsightsPerCountryDTO1).isNotEqualTo(movieInsightsPerCountryDTO2);
        movieInsightsPerCountryDTO1.setId(null);
        assertThat(movieInsightsPerCountryDTO1).isNotEqualTo(movieInsightsPerCountryDTO2);
    }
}
