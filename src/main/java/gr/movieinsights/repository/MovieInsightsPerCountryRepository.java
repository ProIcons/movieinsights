package gr.movieinsights.repository;

import gr.movieinsights.domain.MovieInsightsPerCountry;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MovieInsightsPerCountry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieInsightsPerCountryRepository extends JpaRepository<MovieInsightsPerCountry, Long> {
}
