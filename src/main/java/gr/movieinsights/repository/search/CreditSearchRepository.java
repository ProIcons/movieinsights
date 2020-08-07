package gr.movieinsights.repository.search;

import gr.movieinsights.domain.Credit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Credit} entity.
 */
public interface CreditSearchRepository extends ElasticsearchRepository<Credit, Long> {
}
