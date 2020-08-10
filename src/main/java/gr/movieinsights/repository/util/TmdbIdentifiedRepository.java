package gr.movieinsights.repository.util;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface TmdbIdentifiedRepository<E,K> extends BaseRepository<E,K> {
    @Query("SELECT e.tmdbId FROM #{#entityName} as e")
    List<K> getAllTmdbIds();

    Optional<E> findByTmdbId(K id);

    void deleteByTmdbId(K id);
}
