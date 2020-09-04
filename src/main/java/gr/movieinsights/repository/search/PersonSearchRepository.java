package gr.movieinsights.repository.search;

import gr.movieinsights.domain.elasticsearch.Person;
import gr.movieinsights.repository.util.BaseSearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Person} entity.
 */
public interface PersonSearchRepository extends BaseSearchRepository<Person, Long> {
}
