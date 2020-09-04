package gr.movieinsights.repository;

import gr.movieinsights.domain.Credit;
import gr.movieinsights.repository.util.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Credit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditRepository extends BaseRepository<Credit, Long> {
    @Query("SELECT e.creditId FROM #{#entityName} as e")
    List<String> getAllIDs();

    @Query("select max(id) from Credit")
    long findMaxId();

    Optional<Credit> findByCreditId(String id);

    void deleteByCreditId(String id);

    @Query("select distinct credit from Credit credit left join fetch credit.movie left join fetch credit.person")
    List<Credit> findAllWithEagerRelationships();

    @Query(value = "select distinct credit from Credit credit left join fetch credit.movie left join fetch credit.person",
        countQuery = "select count(distinct credit) from Credit credit")
    Page<Credit> findAllWithEagerRelationships(Pageable pageable);

    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE credit", nativeQuery = true)
    void clear();

}
