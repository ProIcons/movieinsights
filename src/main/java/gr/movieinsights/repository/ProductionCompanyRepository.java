package gr.movieinsights.repository;

import gr.movieinsights.domain.ProductionCompany;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductionCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionCompanyRepository extends JpaRepository<ProductionCompany, Long> {
}
