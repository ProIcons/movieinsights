package gr.movieinsights.repository.search;

import gr.movieinsights.domain.elasticsearch.Genre;
import gr.movieinsights.repository.util.BaseSearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Genre} entity.
 */
public interface GenreSearchRepository extends BaseSearchRepository<Genre, Long> {
}
