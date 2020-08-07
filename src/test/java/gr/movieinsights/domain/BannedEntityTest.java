package gr.movieinsights.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class BannedEntityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BannedEntity.class);
        BannedEntity bannedEntity1 = new BannedEntity();
        bannedEntity1.setId(1L);
        BannedEntity bannedEntity2 = new BannedEntity();
        bannedEntity2.setId(bannedEntity1.getId());
        assertThat(bannedEntity1).isEqualTo(bannedEntity2);
        bannedEntity2.setId(2L);
        assertThat(bannedEntity1).isNotEqualTo(bannedEntity2);
        bannedEntity1.setId(null);
        assertThat(bannedEntity1).isNotEqualTo(bannedEntity2);
    }
}
