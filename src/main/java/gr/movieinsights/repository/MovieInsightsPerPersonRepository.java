package gr.movieinsights.repository;

import gr.movieinsights.domain.MovieInsightsPerPerson;
import gr.movieinsights.repository.util.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data  repository for the MovieInsightsPerPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieInsightsPerPersonRepository extends BaseRepository<MovieInsightsPerPerson, Long> {
    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE movie_insights_per_person CASCADE", nativeQuery = true)
    void clear();
}
