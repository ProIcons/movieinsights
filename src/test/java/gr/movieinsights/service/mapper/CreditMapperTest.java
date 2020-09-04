package gr.movieinsights.service.mapper;

import gr.movieinsights.service.mapper.credit.CreditMapper;
import gr.movieinsights.service.mapper.credit.CreditMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CreditMapperTest {

    private CreditMapper creditMapper;

    @BeforeEach
    public void setUp() {
        creditMapper = new CreditMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(creditMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(creditMapper.fromId(null)).isNull();
    }
}
