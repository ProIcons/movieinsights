package gr.movieinsights.repository.search;

import gr.movieinsights.domain.BannedPersistentEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link BannedPersistentEntity} entity.
 */
public interface BannedPersistentEntitySearchRepository extends ElasticsearchRepository<BannedPersistentEntity, Long> {
}
