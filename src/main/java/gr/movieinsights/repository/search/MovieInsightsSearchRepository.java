package gr.movieinsights.repository.search;

import gr.movieinsights.domain.MovieInsights;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link MovieInsights} entity.
 */
public interface MovieInsightsSearchRepository extends ElasticsearchRepository<MovieInsights, Long> {
}
