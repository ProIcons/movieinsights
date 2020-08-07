package gr.movieinsights.repository;

import gr.movieinsights.domain.Vote;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Vote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
