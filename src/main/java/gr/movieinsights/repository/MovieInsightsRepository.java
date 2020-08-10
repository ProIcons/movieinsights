package gr.movieinsights.repository;

import gr.movieinsights.domain.MovieInsights;
import gr.movieinsights.repository.util.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data  repository for the MovieInsights entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieInsightsRepository extends BaseRepository<MovieInsights, Long> {
    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE movie_insights CASCADE", nativeQuery = true)
    void clear();
}
