package gr.movieinsights.repository;

import gr.movieinsights.domain.MovieInsightsPerCompany;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MovieInsightsPerCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieInsightsPerCompanyRepository extends JpaRepository<MovieInsightsPerCompany, Long> {
}
