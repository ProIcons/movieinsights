package gr.movieinsights.repository;

import gr.movieinsights.domain.MovieInsightsGeneral;
import gr.movieinsights.repository.util.BaseMovieInsightsRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the MovieInsightsGeneral entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieInsightsGeneralRepository extends BaseMovieInsightsRepository<MovieInsightsGeneral, Long> {
    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE movie_insights_general CASCADE", nativeQuery = true)
    void clear();

    @Query(value = "SELECT distinct mi from MovieInsightsGeneral mi inner join fetch mi.movieInsightsPerYears mipy", countQuery =
        "SELECT count(distinct mi) from MovieInsightsGeneral mi")
    List<MovieInsightsGeneral> findAllWithEagerRelationships();

    @Query(value = "SELECT distinct mi from MovieInsightsGeneral mi inner join fetch mi.movieInsightsPerYears mipy where mi.id =:id")
    Optional<MovieInsightsGeneral> findAllWithEagerRelationships(@Param("id") Long id);
}
