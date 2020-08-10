package gr.movieinsights.repository;

import gr.movieinsights.domain.Movie;

import gr.movieinsights.repository.util.ImdbIdentifiedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Movie entity.
 */
@Repository
public interface MovieRepository extends ImdbIdentifiedRepository<Movie, Long> {

    @Query(value = "select distinct movie from Movie movie left join fetch movie.companies left join fetch movie.countries left join fetch movie.genres",
        countQuery = "select count(distinct movie) from Movie movie")
    Page<Movie> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct movie from Movie movie left join fetch movie.companies left join fetch movie.countries left join fetch movie.genres")
    List<Movie> findAllWithEagerRelationships();

    @Query("select movie from Movie movie left join fetch movie.companies left join fetch movie.countries left join fetch movie.genres where movie.id =:id")
    Optional<Movie> findOneWithEagerRelationships(@Param("id") Long id);
}
