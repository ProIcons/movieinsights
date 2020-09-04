package gr.movieinsights.repository.search;

import gr.movieinsights.domain.elasticsearch.ProductionCompany;
import gr.movieinsights.repository.util.BaseSearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ProductionCompany} entity.
 */
public interface ProductionCompanySearchRepository extends BaseSearchRepository<ProductionCompany,Long> {
}
