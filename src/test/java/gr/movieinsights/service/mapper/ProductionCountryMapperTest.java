package gr.movieinsights.service.mapper;

import gr.movieinsights.service.mapper.country.ProductionCountryMapper;
import gr.movieinsights.service.mapper.country.ProductionCountryMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductionCountryMapperTest {

    private ProductionCountryMapper productionCountryMapper;

    @BeforeEach
    public void setUp() {
        productionCountryMapper = new ProductionCountryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productionCountryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productionCountryMapper.fromId(null)).isNull();
    }
}
