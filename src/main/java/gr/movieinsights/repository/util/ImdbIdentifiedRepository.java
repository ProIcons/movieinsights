package gr.movieinsights.repository.util;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ImdbIdentifiedRepository<E, K> extends BaseRepository<E,K> {
   @Query("SELECT e.imdbId FROM #{#entityName} as e")
    List<String> getAllImdbIds();
}
