package gr.movieinsights.repository;

import gr.movieinsights.domain.Genre;
import gr.movieinsights.repository.util.TmdbIdentifiedRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring Data  repository for the Genre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenreRepository extends TmdbIdentifiedRepository<Genre, Long> {
    @Query("select max(id) from Genre")
    long findMaxId();

    @Query("select distinct genre from Genre genre left join fetch genre.movies")
    List<Genre> findAllWithEagerRelationships();

    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE genre CASCADE", nativeQuery = true)
    void clear();
}
