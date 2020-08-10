package gr.movieinsights.repository;

import gr.movieinsights.domain.ProductionCountry;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the ProductionCountry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionCountryRepository extends JpaRepository<ProductionCountry, Long> {
    Optional<ProductionCountry> findByIso31661(String iso);
}
