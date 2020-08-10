package gr.movieinsights.repository.util;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@NoRepositoryBean
public interface ImdbIdentifiedRepository<E, K> extends TmdbIdentifiedRepository<E,K> {
   @Query("SELECT e.imdbId FROM #{#entityName} as e")
    List<String> getAllImdbIds();
}
