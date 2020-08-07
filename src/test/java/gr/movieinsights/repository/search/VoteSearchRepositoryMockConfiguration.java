package gr.movieinsights.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link VoteSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class VoteSearchRepositoryMockConfiguration {

    @MockBean
    private VoteSearchRepository mockVoteSearchRepository;

}
