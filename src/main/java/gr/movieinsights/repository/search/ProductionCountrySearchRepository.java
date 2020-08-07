package gr.movieinsights.repository.search;

import gr.movieinsights.domain.ProductionCountry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ProductionCountry} entity.
 */
public interface ProductionCountrySearchRepository extends ElasticsearchRepository<ProductionCountry, Long> {
}
