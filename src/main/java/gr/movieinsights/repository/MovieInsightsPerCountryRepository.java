package gr.movieinsights.repository;

import gr.movieinsights.domain.MovieInsightsPerCountry;
import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.repository.util.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Spring Data  repository for the MovieInsightsPerCountry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieInsightsPerCountryRepository extends BaseRepository<MovieInsightsPerCountry, Long> {
    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE movie_insights_per_country CASCADE", nativeQuery = true)
    void clear();

    Optional<MovieInsightsPerCountry> findByCountry(ProductionCountry country);

    @Query("SELECT distinct mipc FROM MovieInsightsPerCountry mipc LEFT JOIN FETCH mipc.country c LEFT JOIN fetch mipc.movieInsights LEFT Join fetch mipc.movieInsightsPerYears WHERE c.iso31661 = :iso31661")
    Optional<MovieInsightsPerCountry> findByCountry(@Param("iso31661") String iso31661);
}
