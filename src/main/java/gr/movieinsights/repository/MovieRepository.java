package gr.movieinsights.repository;

import gr.movieinsights.domain.Movie;
import gr.movieinsights.repository.util.ImdbIdentifiedRepository;
import gr.movieinsights.repository.util.TmdbIdentifiedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Movie entity.
 */
@Repository
public interface MovieRepository extends TmdbIdentifiedRepository<Movie, Long>, ImdbIdentifiedRepository<Movie, Long> {

    @Query(value = "select distinct movie from Movie movie left join fetch movie.companies left join fetch movie.countries left join fetch movie.genres",
        countQuery = "select count(distinct movie) from Movie movie")
    Page<Movie> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct movie from Movie movie left join fetch movie.companies left join fetch movie.countries left join fetch movie.genres left join fetch movie.credits")
    List<Movie> findAllWithEagerRelationships();

    @Query("select distinct movie from Movie movie left join fetch movie.companies left join fetch movie.countries left join fetch movie.genres left join fetch movie.credits where movie.releaseDate is not null and YEAR(movie.releaseDate) = :year")
    List<Movie> findAllWithEagerRelationships(@Param("year") int year);

    @Query("select distinct movie from Movie movie left join fetch movie.companies left join fetch movie.countries left join fetch movie.genres left join fetch movie.credits where movie.releaseDate is not null and YEAR(movie.releaseDate) > :year")
    List<Movie> findAllWithEagerRelationshipsWhenYearIsAfterThan(@Param("year") int year);

    List<Movie> findAll();

    @Query("select movie from Movie movie left join fetch movie.companies left join fetch movie.countries left join fetch movie.genres where movie.id =:id")
    Optional<Movie> findOneWithEagerRelationships(@Param("id") Long id);

    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE movie CASCADE", nativeQuery = true)
    void clear();
}
