package gr.movieinsights.repository;

import gr.movieinsights.domain.MovieInsightsPerPerson;
import gr.movieinsights.domain.enumeration.CreditRole;
import gr.movieinsights.repository.util.BaseMovieInsightsRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the MovieInsightsPerPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieInsightsPerPersonRepository extends BaseMovieInsightsRepository<MovieInsightsPerPerson, Long> {
    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE movie_insights_per_person CASCADE", nativeQuery = true)
    void clear();

    @Query("SELECT distinct miy.year FROM MovieInsightsPerPerson mi inner join mi.person p inner join mi.movieInsightsPerYears miy where p.id = :id and mi.as = :role")
    List<Integer> getYears(@Param("id") Long id, @Param("role") CreditRole role);

    Optional<MovieInsightsPerPerson> findByPerson_IdAndAs(Long id, CreditRole role);

    List<MovieInsightsPerPerson> findByPerson_Id(Long id);


    @Query("SELECT distinct mi FROM MovieInsightsPerPerson mi inner join fetch mi.person p inner join fetch mi.movieInsightsPerYears miy where p.id = :id and miy.year = :year")
    List<MovieInsightsPerPerson> findByYear(@Param("id") Long id, @Param("year") int year);
}
