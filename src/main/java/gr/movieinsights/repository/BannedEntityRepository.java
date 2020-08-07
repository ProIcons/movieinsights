package gr.movieinsights.repository;

import gr.movieinsights.domain.BannedEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BannedEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BannedEntityRepository extends JpaRepository<BannedEntity, Long> {
}
