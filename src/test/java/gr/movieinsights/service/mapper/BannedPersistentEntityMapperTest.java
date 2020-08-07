package gr.movieinsights.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BannedPersistentEntityMapperTest {

    private BannedPersistentEntityMapper bannedPersistentEntityMapper;

    @BeforeEach
    public void setUp() {
        bannedPersistentEntityMapper = new BannedPersistentEntityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bannedPersistentEntityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bannedPersistentEntityMapper.fromId(null)).isNull();
    }
}
