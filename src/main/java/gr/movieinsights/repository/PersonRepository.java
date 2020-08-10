package gr.movieinsights.repository;

import gr.movieinsights.domain.Person;
import gr.movieinsights.repository.util.ImdbIdentifiedRepository;
import gr.movieinsights.repository.util.TmdbIdentifiedRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring Data  repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRepository extends ImdbIdentifiedRepository<Person, Long>, TmdbIdentifiedRepository<Person, Long> {
    @Query("select distinct person from Person person left join fetch person.credits")
    List<Person> findAllWithEagerRelationships();

    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE person CASCADE", nativeQuery = true)
    void clear();
}
