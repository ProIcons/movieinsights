package gr.movieinsights.repository;

import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.repository.util.TmdbIdentifiedRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring Data  repository for the ProductionCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionCompanyRepository extends TmdbIdentifiedRepository<ProductionCompany, Long> {
    @Query("select max(id) from ProductionCompany")
    long findMaxId();

    @Query("select distinct company from ProductionCompany company left join fetch company.movies")
    List<ProductionCompany> findAllWithEagerRelationships();

    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE production_company CASCADE", nativeQuery = true)
    void clear();
}
