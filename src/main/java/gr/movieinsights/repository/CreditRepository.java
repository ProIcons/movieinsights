package gr.movieinsights.repository;

import gr.movieinsights.domain.Credit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Credit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    @Query("SELECT e.creditId FROM #{#entityName} as e")
    List<String> getAllIDs();

    @Query("select max(id) from Credit")
    long findMaxId();

    Optional<Credit> findByCreditId(String id);

    void deleteByCreditId(String id);
}
