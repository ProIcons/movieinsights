package gr.movieinsights.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class MovieInsightsPerGenreDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieInsightsPerGenreDTO.class);
        MovieInsightsPerGenreDTO movieInsightsPerGenreDTO1 = new MovieInsightsPerGenreDTO();
        movieInsightsPerGenreDTO1.setId(1L);
        MovieInsightsPerGenreDTO movieInsightsPerGenreDTO2 = new MovieInsightsPerGenreDTO();
        assertThat(movieInsightsPerGenreDTO1).isNotEqualTo(movieInsightsPerGenreDTO2);
        movieInsightsPerGenreDTO2.setId(movieInsightsPerGenreDTO1.getId());
        assertThat(movieInsightsPerGenreDTO1).isEqualTo(movieInsightsPerGenreDTO2);
        movieInsightsPerGenreDTO2.setId(2L);
        assertThat(movieInsightsPerGenreDTO1).isNotEqualTo(movieInsightsPerGenreDTO2);
        movieInsightsPerGenreDTO1.setId(null);
        assertThat(movieInsightsPerGenreDTO1).isNotEqualTo(movieInsightsPerGenreDTO2);
    }
}
