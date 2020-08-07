package gr.movieinsights.repository;

import gr.movieinsights.domain.BannedPersistentEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BannedPersistentEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BannedPersistentEntityRepository extends JpaRepository<BannedPersistentEntity, Long> {
}
