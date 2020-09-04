package gr.movieinsights.repository;

import gr.movieinsights.domain.Genre;
import gr.movieinsights.repository.util.BaseSearchableEntityNonSearchableRepository;
import gr.movieinsights.repository.util.TmdbIdentifiedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface GenreRepository extends TmdbIdentifiedRepository<Genre, Long>, BaseSearchableEntityNonSearchableRepository<Genre, gr.movieinsights.domain.elasticsearch.Genre, Long> {
    @Query("select max(id) from Genre")
    long findMaxId();

    @Query(value = "select distinct genre from Genre genre left join fetch genre.movies")
    @Override
    List<Genre> findAllWithEagerRelationships();

    @Query(value = "select distinct genre from Genre genre left join fetch genre.movies",
        countQuery = "SELECT count(distinct genre) from Genre genre")
    @Override
    Page<Genre> findAllWithEagerRelationships(Pageable pageable);

    @Override
    @Query("select genre.id,count(m.id) from Genre genre left join genre.movies m group by genre.id")
    List<Object[]> findAllWithMovieCountsObjects();

    @Override
    @Query("SELECT new gr.movieinsights.domain.elasticsearch.Genre(genre,count(distinct m.id),sum(v.votes),sum(v.average)) FROM Genre genre left join genre.movies m left join m.vote v group by genre.id")
    List<gr.movieinsights.domain.elasticsearch.Genre> findAllEntitiesAsElasticSearchIndices();

    @Override
    default List<gr.movieinsights.domain.elasticsearch.Genre> findOneAsElasticSearchIndex(Long id) {
        return null;
    }

    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE genre CASCADE", nativeQuery = true)
    void clear();
}
