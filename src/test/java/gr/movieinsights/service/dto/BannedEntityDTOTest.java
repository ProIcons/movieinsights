package gr.movieinsights.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class BannedEntityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BannedEntityDTO.class);
        BannedEntityDTO bannedEntityDTO1 = new BannedEntityDTO();
        bannedEntityDTO1.setId(1L);
        BannedEntityDTO bannedEntityDTO2 = new BannedEntityDTO();
        assertThat(bannedEntityDTO1).isNotEqualTo(bannedEntityDTO2);
        bannedEntityDTO2.setId(bannedEntityDTO1.getId());
        assertThat(bannedEntityDTO1).isEqualTo(bannedEntityDTO2);
        bannedEntityDTO2.setId(2L);
        assertThat(bannedEntityDTO1).isNotEqualTo(bannedEntityDTO2);
        bannedEntityDTO1.setId(null);
        assertThat(bannedEntityDTO1).isNotEqualTo(bannedEntityDTO2);
    }
}
