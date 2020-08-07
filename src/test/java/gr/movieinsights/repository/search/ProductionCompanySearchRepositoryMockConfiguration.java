package gr.movieinsights.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ProductionCompanySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ProductionCompanySearchRepositoryMockConfiguration {

    @MockBean
    private ProductionCompanySearchRepository mockProductionCompanySearchRepository;

}
