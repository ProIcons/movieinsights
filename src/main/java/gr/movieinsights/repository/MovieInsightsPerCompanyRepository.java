package gr.movieinsights.repository;

import gr.movieinsights.domain.MovieInsightsPerCompany;
import gr.movieinsights.domain.ProductionCompany;
import gr.movieinsights.repository.util.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Spring Data  repository for the MovieInsightsPerCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieInsightsPerCompanyRepository extends BaseRepository<MovieInsightsPerCompany, Long> {
    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE movie_insights_per_company CASCADE", nativeQuery = true)
    void clear();

    Optional<MovieInsightsPerCompany> findByCompany(ProductionCompany productionCompany);
    Optional<MovieInsightsPerCompany> findByCompany_Id(Long id);
}
