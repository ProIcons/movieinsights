package gr.movieinsights.repository;

import gr.movieinsights.domain.MovieInsightsPerPerson;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MovieInsightsPerPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieInsightsPerPersonRepository extends JpaRepository<MovieInsightsPerPerson, Long> {
}
