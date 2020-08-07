package gr.movieinsights.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link MovieInsightsPerCountrySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class MovieInsightsPerCountrySearchRepositoryMockConfiguration {

    @MockBean
    private MovieInsightsPerCountrySearchRepository mockMovieInsightsPerCountrySearchRepository;

}
