package gr.movieinsights.repository.search;

import gr.movieinsights.domain.Movie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Movie} entity.
 */
public interface MovieSearchRepository extends ElasticsearchRepository<Movie, Long> {
}
