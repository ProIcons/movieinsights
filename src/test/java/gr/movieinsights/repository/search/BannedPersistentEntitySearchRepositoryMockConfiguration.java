package gr.movieinsights.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link BannedPersistentEntitySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class BannedPersistentEntitySearchRepositoryMockConfiguration {

    @MockBean
    private BannedPersistentEntitySearchRepository mockBannedPersistentEntitySearchRepository;

}
