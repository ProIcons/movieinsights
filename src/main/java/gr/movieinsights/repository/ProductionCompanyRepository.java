package gr.movieinsights.repository;

import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.repository.util.BaseSearchableEntityNonSearchableRepository;
import gr.movieinsights.repository.util.TmdbIdentifiedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface ProductionCompanyRepository extends TmdbIdentifiedRepository<ProductionCompany, Long>, BaseSearchableEntityNonSearchableRepository<ProductionCompany, gr.movieinsights.domain.elasticsearch.ProductionCompany, Long> {
    @Query("select max(id) from ProductionCompany")
    long findMaxId();

    @Query(value = "select distinct company from ProductionCompany company left join fetch company.movies")
    @Override
    List<ProductionCompany> findAllWithEagerRelationships();

    @Query(value = "select distinct company from ProductionCompany company left join fetch company.movies",
        countQuery = "select count(distinct company) from ProductionCompany company")
    @Override
    Page<ProductionCompany> findAllWithEagerRelationships(Pageable pageable);

    @Override
    @Query("select company.id,count(m.id) from ProductionCompany company left join company.movies m group by company.id")
    List<Object[]> findAllWithMovieCountsObjects();


    @Override
    @Query("SELECT new gr.movieinsights.domain.elasticsearch.ProductionCompany(company,count(distinct m.id),sum(v.votes),sum(v.average)) FROM ProductionCompany company left join company.movies m left join m.vote v group by company.id")
    List<gr.movieinsights.domain.elasticsearch.ProductionCompany> findAllEntitiesAsElasticSearchIndices();

    @Override
    default List<gr.movieinsights.domain.elasticsearch.ProductionCompany> findOneAsElasticSearchIndex(Long id) {
        return null;
    }

    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE production_company CASCADE", nativeQuery = true)
    void clear();
}
