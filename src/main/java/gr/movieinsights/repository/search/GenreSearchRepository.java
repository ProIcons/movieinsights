package gr.movieinsights.repository.search;

import gr.movieinsights.domain.Genre;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Genre} entity.
 */
public interface GenreSearchRepository extends ElasticsearchRepository<Genre, Long> {
}
