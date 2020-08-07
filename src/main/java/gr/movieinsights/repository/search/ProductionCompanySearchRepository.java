package gr.movieinsights.repository.search;

import gr.movieinsights.domain.ProductionCompany;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ProductionCompany} entity.
 */
public interface ProductionCompanySearchRepository extends ElasticsearchRepository<ProductionCompany, Long> {
}
