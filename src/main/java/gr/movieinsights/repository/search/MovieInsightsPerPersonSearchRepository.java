package gr.movieinsights.repository.search;

import gr.movieinsights.domain.MovieInsightsPerPerson;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link MovieInsightsPerPerson} entity.
 */
public interface MovieInsightsPerPersonSearchRepository extends ElasticsearchRepository<MovieInsightsPerPerson, Long> {
}
