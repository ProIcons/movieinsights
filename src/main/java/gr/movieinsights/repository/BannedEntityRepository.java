package gr.movieinsights.repository;

import gr.movieinsights.domain.BannedEntity;
import gr.movieinsights.repository.util.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data  repository for the BannedEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BannedEntityRepository extends BaseRepository<BannedEntity, Long> {
    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE banned_entity", nativeQuery = true)
    void clear();
}
