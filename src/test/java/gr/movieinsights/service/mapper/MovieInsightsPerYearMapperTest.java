package gr.movieinsights.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MovieInsightsPerYearMapperTest {

    private MovieInsightsPerYearMapper movieInsightsPerYearMapper;

    @BeforeEach
    public void setUp() {
        movieInsightsPerYearMapper = new MovieInsightsPerYearMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(movieInsightsPerYearMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(movieInsightsPerYearMapper.fromId(null)).isNull();
    }
}
