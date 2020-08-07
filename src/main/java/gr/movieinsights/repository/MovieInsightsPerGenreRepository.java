package gr.movieinsights.repository;

import gr.movieinsights.domain.MovieInsightsPerGenre;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MovieInsightsPerGenre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieInsightsPerGenreRepository extends JpaRepository<MovieInsightsPerGenre, Long> {
}
