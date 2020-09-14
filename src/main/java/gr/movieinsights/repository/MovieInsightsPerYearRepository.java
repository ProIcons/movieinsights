package gr.movieinsights.repository;

import gr.movieinsights.domain.MovieInsightsPerYear;
import gr.movieinsights.domain.enumeration.CreditRole;
import gr.movieinsights.repository.util.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Spring Data  repository for the MovieInsightsPerYear entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieInsightsPerYearRepository extends BaseRepository<MovieInsightsPerYear, Long> {
    @Transactional
    @Modifying
    @Override
    @Query(value = "TRUNCATE TABLE movie_insights_per_year CASCADE", nativeQuery = true)
    void clear();

    @Query("" +
        "SELECT distinct movie_insights_per_year " +
        "FROM MovieInsightsPerYear movie_insights_per_year " +
        "left join fetch movie_insights_per_year.movieInsights " +
        "where movie_insights_per_year.movieInsightsPerCompany is null " +
        "and movie_insights_per_year.movieInsightsPerCountry is null " +
        "and movie_insights_per_year.movieInsightsPerGenre is null " +
        "and movie_insights_per_year.movieInsightsPerPerson is null " +
        "and movie_insights_per_year.movieInsightsGeneral is null " +
        "and movie_insights_per_year.year = :year")
    Optional<MovieInsightsPerYear> findByYear(@Param("year") int year);

    @Query("SELECT distinct miy FROM MovieInsightsPerYear miy inner join miy.movieInsightsPerCompany mic inner join mic.company c on c.id = :companyId where miy.year = :year")
    Optional<MovieInsightsPerYear> findByCompany(@Param("companyId") long companyId, @Param("year") int year);

    @Query("SELECT distinct miy FROM MovieInsightsPerYear miy inner join miy.movieInsightsPerCountry mic inner join mic.country c on lower(c.iso31661) = lower(:countryIso) where miy.year = :year")
    Optional<MovieInsightsPerYear> findByCountry(@Param("countryIso") String countryIso, @Param("year") int year);

    @Query("SELECT distinct miy FROM MovieInsightsPerYear miy inner join miy.movieInsightsPerCountry mic inner join mic.country c on c.id = :countryId where miy.year = :year")
    Optional<MovieInsightsPerYear> findByCountryId(@Param("countryId") long countryId, @Param("year") int year);

    @Query("SELECT distinct miy FROM MovieInsightsPerYear miy inner join miy.movieInsightsPerGenre mic inner join mic.genre g on g.id = :genreId where miy.year = :year")
    Optional<MovieInsightsPerYear> findByGenre(@Param("genreId") long genreId, @Param("year") int year);

    @Query("SELECT distinct miy FROM MovieInsightsPerYear miy inner join miy.movieInsightsPerGenre mic inner join mic.genre g on lower(g.name) = lower(:genreName) where miy.year = :year")
    Optional<MovieInsightsPerYear> findByGenre(@Param("genreName") String genreName, @Param("year") int year);


    @Query("SELECT distinct miy FROM MovieInsightsPerYear miy inner join miy.movieInsightsPerPerson mic inner join mic.person p on p.id = :personId where miy.year = :year")
    Optional<MovieInsightsPerYear> findByPerson(@Param("personId") long personId, @Param("year") int year);

    @Query("SELECT distinct miy FROM MovieInsightsPerYear miy inner join miy.movieInsightsPerPerson mic on mic.as = :role inner join mic.person p on p.id = :personId where miy.year = :year")
    Optional<MovieInsightsPerYear> findByPerson(@Param("personId") long personId, @Param("role") CreditRole role, @Param("year") int year);


    @Query("SELECT distinct miy FROM MovieInsightsPerYear miy where miy.year = :year and miy.movieInsightsGeneral is not null")
    Optional<MovieInsightsPerYear> findGeneral(@Param("year") int year);

}
