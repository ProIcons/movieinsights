package gr.movieinsights.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MovieInsightsPerCompanyMapperTest {

    private MovieInsightsPerCompanyMapper movieInsightsPerCompanyMapper;

    @BeforeEach
    public void setUp() {
        movieInsightsPerCompanyMapper = new MovieInsightsPerCompanyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(movieInsightsPerCompanyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(movieInsightsPerCompanyMapper.fromId(null)).isNull();
    }
}
