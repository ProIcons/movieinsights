package gr.movieinsights.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class MovieInsightsPerPersonDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieInsightsPerPersonDTO.class);
        MovieInsightsPerPersonDTO movieInsightsPerPersonDTO1 = new MovieInsightsPerPersonDTO();
        movieInsightsPerPersonDTO1.setId(1L);
        MovieInsightsPerPersonDTO movieInsightsPerPersonDTO2 = new MovieInsightsPerPersonDTO();
        assertThat(movieInsightsPerPersonDTO1).isNotEqualTo(movieInsightsPerPersonDTO2);
        movieInsightsPerPersonDTO2.setId(movieInsightsPerPersonDTO1.getId());
        assertThat(movieInsightsPerPersonDTO1).isEqualTo(movieInsightsPerPersonDTO2);
        movieInsightsPerPersonDTO2.setId(2L);
        assertThat(movieInsightsPerPersonDTO1).isNotEqualTo(movieInsightsPerPersonDTO2);
        movieInsightsPerPersonDTO1.setId(null);
        assertThat(movieInsightsPerPersonDTO1).isNotEqualTo(movieInsightsPerPersonDTO2);
    }
}
