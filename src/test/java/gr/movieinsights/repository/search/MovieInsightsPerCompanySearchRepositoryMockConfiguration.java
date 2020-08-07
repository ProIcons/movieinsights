package gr.movieinsights.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link MovieInsightsPerCompanySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class MovieInsightsPerCompanySearchRepositoryMockConfiguration {

    @MockBean
    private MovieInsightsPerCompanySearchRepository mockMovieInsightsPerCompanySearchRepository;

}
