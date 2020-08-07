package gr.movieinsights.repository.search;

import gr.movieinsights.domain.MovieInsightsPerCountry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link MovieInsightsPerCountry} entity.
 */
public interface MovieInsightsPerCountrySearchRepository extends ElasticsearchRepository<MovieInsightsPerCountry, Long> {
}
