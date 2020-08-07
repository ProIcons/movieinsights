package gr.movieinsights.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link MovieInsightsSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class MovieInsightsSearchRepositoryMockConfiguration {

    @MockBean
    private MovieInsightsSearchRepository mockMovieInsightsSearchRepository;

}
