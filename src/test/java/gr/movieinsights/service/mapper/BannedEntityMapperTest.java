package gr.movieinsights.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BannedEntityMapperTest {

    private BannedEntityMapper bannedEntityMapper;

    @BeforeEach
    public void setUp() {
        bannedEntityMapper = new BannedEntityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bannedEntityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bannedEntityMapper.fromId(null)).isNull();
    }
}
