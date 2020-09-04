package gr.movieinsights.repository;

import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.repository.util.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Spring Data  repository for the MovieInsightsPerYear entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieInsightsPerYearRepository extends BaseRepository<MovieInsightsPerYear, Long> {
    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE movie_insights_per_year CASCADE", nativeQuery = true)
    void clear();

    @Query("" +
        "SELECT distinct movie_insights_per_year " +
        "FROM MovieInsightsPerYear movie_insights_per_year " +
        "left join fetch movie_insights_per_year.movieInsights " +
        "where movie_insights_per_year.movieInsightsPerCompany is null " +
        "and movie_insights_per_year.movieInsightsPerCountry is null " +
        "and movie_insights_per_year.movieInsightsPerGenre is null " +
        "and movie_insights_per_year.movieInsightsPerPerson is null " +
        "and movie_insights_per_year.movieInsightsGeneral is null " +
        "and movie_insights_per_year.year = :year")
    Optional<MovieInsightsPerYear> findByYear(@Param("year") int year);
}
