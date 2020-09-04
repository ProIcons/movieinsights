package gr.movieinsights.repository.search;

import gr.movieinsights.domain.elasticsearch.ProductionCountry;
import gr.movieinsights.repository.util.BaseSearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ProductionCountry} entity.
 */
public interface ProductionCountrySearchRepository extends BaseSearchRepository<ProductionCountry, Long> {
}
