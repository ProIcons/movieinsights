package gr.movieinsights.service.mapper;

import gr.movieinsights.service.mapper.company.ProductionCompanyMapper;
import gr.movieinsights.service.mapper.company.ProductionCompanyMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductionCompanyMapperTest {

    private ProductionCompanyMapper productionCompanyMapper;

    @BeforeEach
    public void setUp() {
        productionCompanyMapper = new ProductionCompanyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productionCompanyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productionCompanyMapper.fromId(null)).isNull();
    }
}
