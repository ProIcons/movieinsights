package gr.movieinsights.repository.util;

import gr.movieinsights.domain.IdentifiedEntity;
import gr.movieinsights.domain.MovieInsightsPerYear;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseMovieInsightsRepository<TEntity extends IdentifiedEntity, TKey> extends BaseRepository<TEntity, TKey> {

    @Query("SELECT distinct mi FROM #{#entityName} mi INNER JOIN FETCH mi.movieInsightsPerYears py INNER join fetch py.movieInsights")
    Optional<TEntity> findFirstEager();

    @Override
    @Query("SELECT distinct mi FROM #{#entityName} mi inner join fetch mi.movieInsightsPerYears")
    Optional<TEntity> findFirst();

    default Optional<MovieInsightsPerYear> findByIdAndYear(TEntity entity, int year) {
        return findByIdAndYear(entity.getId(), year);
    }

    @Query("SELECT distinct miy.year FROM #{#entityName} mi inner join mi.movieInsightsPerYears miy")
    List<Integer> getYears();

    @Query("SELECT distinct miy.year FROM #{#entityName} mi inner join mi.movieInsightsPerYears miy where mi.id = :id")
    List<Integer> getYears(@Param("id") Long id);

    @Query("SELECT distinct py FROM #{#entityName} mi INNER JOIN mi.movieInsightsPerYears py WHERE mi.id = :entityId and py.year = :year")
    Optional<MovieInsightsPerYear> findByIdAndYear(@Param("entityId") Long entityId, @Param("year") int year);
}
