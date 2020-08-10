package gr.movieinsights.repository;

import gr.movieinsights.domain.Vote;
import gr.movieinsights.repository.util.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data  repository for the Vote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoteRepository extends BaseRepository<Vote, Long> {
    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE vote CASCADE", nativeQuery = true)
    void clear();
}
