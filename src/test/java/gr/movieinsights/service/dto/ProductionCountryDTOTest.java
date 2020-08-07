package gr.movieinsights.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class ProductionCountryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionCountryDTO.class);
        ProductionCountryDTO productionCountryDTO1 = new ProductionCountryDTO();
        productionCountryDTO1.setId(1L);
        ProductionCountryDTO productionCountryDTO2 = new ProductionCountryDTO();
        assertThat(productionCountryDTO1).isNotEqualTo(productionCountryDTO2);
        productionCountryDTO2.setId(productionCountryDTO1.getId());
        assertThat(productionCountryDTO1).isEqualTo(productionCountryDTO2);
        productionCountryDTO2.setId(2L);
        assertThat(productionCountryDTO1).isNotEqualTo(productionCountryDTO2);
        productionCountryDTO1.setId(null);
        assertThat(productionCountryDTO1).isNotEqualTo(productionCountryDTO2);
    }
}
