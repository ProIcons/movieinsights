package gr.movieinsights.repository.search;

import gr.movieinsights.domain.Vote;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Vote} entity.
 */
public interface VoteSearchRepository extends ElasticsearchRepository<Vote, Long> {
}
