package gr.movieinsights.service.mapper;

import gr.movieinsights.service.mapper.movieinsights.country.MovieInsightsPerCountryMapper;
import gr.movieinsights.service.mapper.movieinsights.country.MovieInsightsPerCountryMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MovieInsightsPerCountryMapperTest {

    private MovieInsightsPerCountryMapper movieInsightsPerCountryMapper;

    @BeforeEach
    public void setUp() {
        movieInsightsPerCountryMapper = new MovieInsightsPerCountryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(movieInsightsPerCountryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(movieInsightsPerCountryMapper.fromId(null)).isNull();
    }
}
