package gr.movieinsights.repository.search;

import gr.movieinsights.domain.MovieInsightsPerYear;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link MovieInsightsPerYear} entity.
 */
public interface MovieInsightsPerYearSearchRepository extends ElasticsearchRepository<MovieInsightsPerYear, Long> {
}
