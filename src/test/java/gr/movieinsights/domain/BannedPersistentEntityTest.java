package gr.movieinsights.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import gr.movieinsights.web.rest.TestUtil;

public class BannedPersistentEntityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BannedPersistentEntity.class);
        BannedPersistentEntity bannedPersistentEntity1 = new BannedPersistentEntity();
        bannedPersistentEntity1.setId(1L);
        BannedPersistentEntity bannedPersistentEntity2 = new BannedPersistentEntity();
        bannedPersistentEntity2.setId(bannedPersistentEntity1.getId());
        assertThat(bannedPersistentEntity1).isEqualTo(bannedPersistentEntity2);
        bannedPersistentEntity2.setId(2L);
        assertThat(bannedPersistentEntity1).isNotEqualTo(bannedPersistentEntity2);
        bannedPersistentEntity1.setId(null);
        assertThat(bannedPersistentEntity1).isNotEqualTo(bannedPersistentEntity2);
    }
}
