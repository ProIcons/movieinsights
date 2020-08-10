package gr.movieinsights.repository;

import gr.movieinsights.domain.ProductionCompany;

import gr.movieinsights.repository.util.TmdbIdentifiedRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductionCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionCompanyRepository extends TmdbIdentifiedRepository<ProductionCompany, Long> {
}
