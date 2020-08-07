package gr.movieinsights.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class ProductionCompanyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionCompany.class);
        ProductionCompany productionCompany1 = new ProductionCompany();
        productionCompany1.setId(1L);
        ProductionCompany productionCompany2 = new ProductionCompany();
        productionCompany2.setId(productionCompany1.getId());
        assertThat(productionCompany1).isEqualTo(productionCompany2);
        productionCompany2.setId(2L);
        assertThat(productionCompany1).isNotEqualTo(productionCompany2);
        productionCompany1.setId(null);
        assertThat(productionCompany1).isNotEqualTo(productionCompany2);
    }
}
