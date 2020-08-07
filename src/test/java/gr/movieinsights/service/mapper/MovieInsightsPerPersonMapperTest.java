package gr.movieinsights.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MovieInsightsPerPersonMapperTest {

    private MovieInsightsPerPersonMapper movieInsightsPerPersonMapper;

    @BeforeEach
    public void setUp() {
        movieInsightsPerPersonMapper = new MovieInsightsPerPersonMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(movieInsightsPerPersonMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(movieInsightsPerPersonMapper.fromId(null)).isNull();
    }
}
