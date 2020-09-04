package gr.movieinsights.repository;

import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.repository.util.BaseSearchableEntityNonSearchableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Spring Data  repository for the ProductionCountry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionCountryRepository extends BaseSearchableEntityNonSearchableRepository<ProductionCountry, gr.movieinsights.domain.elasticsearch.ProductionCountry,Long> {
    Optional<ProductionCountry> findByIso31661(String iso);

    @Query("select max(id) from ProductionCountry")
    long findMaxId();

    @Query(value = "select distinct country from ProductionCountry country left join country.movies")
    @Override
    List<ProductionCountry> findAllWithEagerRelationships();

    @Query(value = "select distinct country from ProductionCountry country left join country.movies",
        countQuery = "select count(distinct country) from ProductionCountry country")
    @Override
    Page<ProductionCountry> findAllWithEagerRelationships(Pageable pageable);

    @Query("select country.id,count(m.id) from ProductionCountry country left join country.movies m group by country.id")
    @Override
    List<Object[]> findAllWithMovieCountsObjects();

    default Map<Long, Long> findAllWithMovieCounts() {
        return findAllWithMovieCountsObjects().stream().collect(Collectors.toMap(o -> (Long) o[0], o -> (Long) o[1]));
    }

    @Override
    @Query("SELECT new gr.movieinsights.domain.elasticsearch.ProductionCountry(country,count(distinct m.id),sum(v.votes),sum(v.average)) FROM ProductionCountry country left join country.movies m left join m.vote v group by country.id")
    List<gr.movieinsights.domain.elasticsearch.ProductionCountry> findAllEntitiesAsElasticSearchIndices();

    @Override
    default List<gr.movieinsights.domain.elasticsearch.ProductionCountry> findOneAsElasticSearchIndex(Long id) {
        return null;
    }

    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE production_country CASCADE", nativeQuery = true)
    void clear();
}
