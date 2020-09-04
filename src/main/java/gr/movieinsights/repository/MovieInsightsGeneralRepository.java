package gr.movieinsights.repository;

import gr.movieinsights.domain.MovieInsightsGeneral;
import gr.movieinsights.repository.util.BaseMovieInsightsRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Query("SELECT DISTINCT miy FROM MovieInsightsPerYear miy WHERE miy.movieInsightsGeneral is not null and miy.year = :year")
    Optional<MovieInsightsGeneral> findByYear(@Param("year") int year);
}
