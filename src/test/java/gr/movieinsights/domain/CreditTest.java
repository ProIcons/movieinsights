package gr.movieinsights.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class CreditTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Credit.class);
        Credit credit1 = new Credit();
        credit1.setId(1L);
        Credit credit2 = new Credit();
        credit2.setId(credit1.getId());
        assertThat(credit1).isEqualTo(credit2);
        credit2.setId(2L);
        assertThat(credit1).isNotEqualTo(credit2);
        credit1.setId(null);
        assertThat(credit1).isNotEqualTo(credit2);
    }
}
