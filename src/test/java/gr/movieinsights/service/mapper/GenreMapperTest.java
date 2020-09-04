package gr.movieinsights.service.mapper;

import gr.movieinsights.service.mapper.genre.GenreMapper;
import gr.movieinsights.service.mapper.genre.GenreMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GenreMapperTest {

    private GenreMapper genreMapper;

    @BeforeEach
    public void setUp() {
        genreMapper = new GenreMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(genreMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(genreMapper.fromId(null)).isNull();
    }
}
