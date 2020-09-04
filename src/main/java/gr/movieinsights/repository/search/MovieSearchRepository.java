package gr.movieinsights.repository.search;

import gr.movieinsights.domain.Movie;
import gr.movieinsights.repository.util.BaseSearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Movie} entity.
 */
public interface MovieSearchRepository extends BaseSearchRepository<Movie, Long> {
}
