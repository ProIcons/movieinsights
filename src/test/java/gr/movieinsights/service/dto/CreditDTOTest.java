package gr.movieinsights.service.dto;

import gr.movieinsights.service.dto.credit.CreditDTO;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class CreditDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditDTO.class);
        CreditDTO creditDTO1 = new CreditDTO();
        creditDTO1.setId(1L);
        CreditDTO creditDTO2 = new CreditDTO();
        assertThat(creditDTO1).isNotEqualTo(creditDTO2);
        creditDTO2.setId(creditDTO1.getId());
        assertThat(creditDTO1).isEqualTo(creditDTO2);
        creditDTO2.setId(2L);
        assertThat(creditDTO1).isNotEqualTo(creditDTO2);
        creditDTO1.setId(null);
        assertThat(creditDTO1).isNotEqualTo(creditDTO2);
    }
}
