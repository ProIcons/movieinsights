package gr.movieinsights.repository;

import gr.movieinsights.domain.MovieInsights;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MovieInsights entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieInsightsRepository extends JpaRepository<MovieInsights, Long> {
}
