package gr.movieinsights.repository;

import gr.movieinsights.domain.MovieInsightsPerCountry;
import gr.movieinsights.repository.util.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
