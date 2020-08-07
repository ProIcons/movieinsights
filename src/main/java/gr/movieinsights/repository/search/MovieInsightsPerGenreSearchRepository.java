package gr.movieinsights.repository.search;

import gr.movieinsights.domain.MovieInsightsPerGenre;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link MovieInsightsPerGenre} entity.
 */
public interface MovieInsightsPerGenreSearchRepository extends ElasticsearchRepository<MovieInsightsPerGenre, Long> {
}
