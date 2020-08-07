package gr.movieinsights.repository;

import gr.movieinsights.domain.MovieInsightsPerYear;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MovieInsightsPerYear entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieInsightsPerYearRepository extends JpaRepository<MovieInsightsPerYear, Long> {
}
