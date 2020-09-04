package gr.movieinsights.service.mapper;

import gr.movieinsights.service.mapper.movieinsights.MovieInsightsMapper;
import gr.movieinsights.service.mapper.movieinsights.MovieInsightsMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MovieInsightsMapperTest {

    private MovieInsightsMapper movieInsightsMapper;

    @BeforeEach
    public void setUp() {
        movieInsightsMapper = new MovieInsightsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(movieInsightsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(movieInsightsMapper.fromId(null)).isNull();
    }
}
