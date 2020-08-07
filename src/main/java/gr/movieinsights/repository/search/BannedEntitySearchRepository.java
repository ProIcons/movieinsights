package gr.movieinsights.repository.search;

import gr.movieinsights.domain.BannedEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link BannedEntity} entity.
 */
public interface BannedEntitySearchRepository extends ElasticsearchRepository<BannedEntity, Long> {
}
