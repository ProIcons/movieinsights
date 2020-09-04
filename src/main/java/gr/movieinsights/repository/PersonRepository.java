package gr.movieinsights.repository;

import gr.movieinsights.domain.Person;
import gr.movieinsights.repository.util.BaseSearchableEntityNonSearchableRepository;
import gr.movieinsights.repository.util.ImdbIdentifiedRepository;
import gr.movieinsights.repository.util.TmdbIdentifiedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface PersonRepository extends ImdbIdentifiedRepository<Person, Long>, TmdbIdentifiedRepository<Person, Long>, BaseSearchableEntityNonSearchableRepository<Person, gr.movieinsights.domain.elasticsearch.Person, Long> {
    @Query(value = "select distinct person from Person person left join fetch person.credits")
    @Override
    List<Person> findAllWithEagerRelationships();

    @Query(value = "select distinct person from Person person left join fetch person.credits c left join fetch c.movie m",
        countQuery = "select count(distinct person) from Person person")
    @Override
    Page<Person> findAllWithEagerRelationships(Pageable pageable);

    @Override
    @Query(value = "select p.id,count(distinct c.movie) from Person p left join p.credits c group by p.id")
    List<Object[]> findAllWithMovieCountsObjects();

    @Override
    @Query("SELECT new gr.movieinsights.domain.elasticsearch.Person(person,count(distinct m.id),sum(v.votes),sum(v.average)) FROM Person person left join person.credits credit left join credit.movie m left join  m.vote v group by person.id")
    List<gr.movieinsights.domain.elasticsearch.Person> findAllEntitiesAsElasticSearchIndices();

    @Override
    @Query(value = "SELECT 'TODO IMPLEMENT ME'",nativeQuery = true)
    List<gr.movieinsights.domain.elasticsearch.Person> findOneAsElasticSearchIndex(Long id);

    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE person CASCADE", nativeQuery = true)
    void clear();
}
