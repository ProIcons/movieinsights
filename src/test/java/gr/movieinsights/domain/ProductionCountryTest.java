package gr.movieinsights.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class ProductionCountryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionCountry.class);
        ProductionCountry productionCountry1 = new ProductionCountry();
        productionCountry1.setId(1L);
        ProductionCountry productionCountry2 = new ProductionCountry();
        productionCountry2.setId(productionCountry1.getId());
        assertThat(productionCountry1).isEqualTo(productionCountry2);
        productionCountry2.setId(2L);
        assertThat(productionCountry1).isNotEqualTo(productionCountry2);
        productionCountry1.setId(null);
        assertThat(productionCountry1).isNotEqualTo(productionCountry2);
    }
}
