package gr.movieinsights.repository.search;

import gr.movieinsights.domain.Image;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Image} entity.
 */
public interface ImageSearchRepository extends ElasticsearchRepository<Image, Long> {
}
