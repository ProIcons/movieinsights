package gr.movieinsights.repository;

import gr.movieinsights.domain.Person;

import gr.movieinsights.repository.util.ImdbIdentifiedRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRepository extends ImdbIdentifiedRepository<Person, Long> {
}
