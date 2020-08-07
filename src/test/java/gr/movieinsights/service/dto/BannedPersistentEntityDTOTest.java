package gr.movieinsights.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class BannedPersistentEntityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BannedPersistentEntityDTO.class);
        BannedPersistentEntityDTO bannedPersistentEntityDTO1 = new BannedPersistentEntityDTO();
        bannedPersistentEntityDTO1.setId(1L);
        BannedPersistentEntityDTO bannedPersistentEntityDTO2 = new BannedPersistentEntityDTO();
        assertThat(bannedPersistentEntityDTO1).isNotEqualTo(bannedPersistentEntityDTO2);
        bannedPersistentEntityDTO2.setId(bannedPersistentEntityDTO1.getId());
        assertThat(bannedPersistentEntityDTO1).isEqualTo(bannedPersistentEntityDTO2);
        bannedPersistentEntityDTO2.setId(2L);
        assertThat(bannedPersistentEntityDTO1).isNotEqualTo(bannedPersistentEntityDTO2);
        bannedPersistentEntityDTO1.setId(null);
        assertThat(bannedPersistentEntityDTO1).isNotEqualTo(bannedPersistentEntityDTO2);
    }
}
