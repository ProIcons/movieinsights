package gr.movieinsights.service.mapper;

import gr.movieinsights.service.mapper.movieinsights.genre.MovieInsightsPerGenreMapper;
import gr.movieinsights.service.mapper.movieinsights.genre.MovieInsightsPerGenreMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MovieInsightsPerGenreMapperTest {

    private MovieInsightsPerGenreMapper movieInsightsPerGenreMapper;

    @BeforeEach
    public void setUp() {
        movieInsightsPerGenreMapper = new MovieInsightsPerGenreMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(movieInsightsPerGenreMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(movieInsightsPerGenreMapper.fromId(null)).isNull();
    }
}
