package gr.movieinsights.repository.search;

import gr.movieinsights.domain.MovieInsightsPerCompany;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link MovieInsightsPerCompany} entity.
 */
public interface MovieInsightsPerCompanySearchRepository extends ElasticsearchRepository<MovieInsightsPerCompany, Long> {
}
