package gr.movieinsights.service.dto;

import gr.movieinsights.service.dto.company.ProductionCompanyDTO;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class ProductionCompanyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionCompanyDTO.class);
        ProductionCompanyDTO productionCompanyDTO1 = new ProductionCompanyDTO();
        productionCompanyDTO1.setId(1L);
        ProductionCompanyDTO productionCompanyDTO2 = new ProductionCompanyDTO();
        assertThat(productionCompanyDTO1).isNotEqualTo(productionCompanyDTO2);
        productionCompanyDTO2.setId(productionCompanyDTO1.getId());
        assertThat(productionCompanyDTO1).isEqualTo(productionCompanyDTO2);
        productionCompanyDTO2.setId(2L);
        assertThat(productionCompanyDTO1).isNotEqualTo(productionCompanyDTO2);
        productionCompanyDTO1.setId(null);
        assertThat(productionCompanyDTO1).isNotEqualTo(productionCompanyDTO2);
    }
}
