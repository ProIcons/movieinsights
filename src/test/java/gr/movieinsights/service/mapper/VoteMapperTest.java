package gr.movieinsights.service.mapper;

import gr.movieinsights.service.mapper.vote.VoteMapper;
import gr.movieinsights.service.mapper.vote.VoteMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteMapperTest {

    private VoteMapper voteMapper;

    @BeforeEach
    public void setUp() {
        voteMapper = new VoteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(voteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(voteMapper.fromId(null)).isNull();
    }
}
