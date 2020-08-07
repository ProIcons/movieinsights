package gr.movieinsights.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class MovieInsightsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieInsightsDTO.class);
        MovieInsightsDTO movieInsightsDTO1 = new MovieInsightsDTO();
        movieInsightsDTO1.setId(1L);
        MovieInsightsDTO movieInsightsDTO2 = new MovieInsightsDTO();
        assertThat(movieInsightsDTO1).isNotEqualTo(movieInsightsDTO2);
        movieInsightsDTO2.setId(movieInsightsDTO1.getId());
        assertThat(movieInsightsDTO1).isEqualTo(movieInsightsDTO2);
        movieInsightsDTO2.setId(2L);
        assertThat(movieInsightsDTO1).isNotEqualTo(movieInsightsDTO2);
        movieInsightsDTO1.setId(null);
        assertThat(movieInsightsDTO1).isNotEqualTo(movieInsightsDTO2);
    }
}
