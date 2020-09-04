package gr.movieinsights.repository.util;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseMovieInsightsRepository<TEntity,TKey> extends BaseRepository<TEntity,TKey> {

    @Override
    @Query("SELECT distinct mi FROM #{#entityName} mi INNER JOIN FETCH mi.movieInsightsPerYears py INNER join fetch py.movieInsights")
    Optional<TEntity> findFirst();


}
