package gr.movieinsights.repository;

import gr.movieinsights.domain.ProductionCountry;
import gr.movieinsights.repository.util.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ProductionCountry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionCountryRepository extends BaseRepository<ProductionCountry, Long> {
    Optional<ProductionCountry> findByIso31661(String iso);
    @Query("select max(id) from ProductionCountry")
    long findMaxId();

    @Query("select distinct country from ProductionCountry country left join fetch country.movies")
    List<ProductionCountry> findAllWithEagerRelationships();

    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE production_country CASCADE", nativeQuery = true)
    void clear();
}
