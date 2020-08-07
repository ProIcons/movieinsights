package gr.movieinsights.repository;

import gr.movieinsights.domain.ProductionCountry;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductionCountry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionCountryRepository extends JpaRepository<ProductionCountry, Long> {
}
