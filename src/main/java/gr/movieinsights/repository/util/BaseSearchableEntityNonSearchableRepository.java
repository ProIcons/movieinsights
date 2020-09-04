package gr.movieinsights.repository.util;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@NoRepositoryBean
public interface BaseSearchableEntityNonSearchableRepository<TEntity,TESEntity,TKey> extends BaseSearchableEntityRepository<TEntity,TESEntity,TKey> {
    List<Object[]> findAllWithMovieCountsObjects();
    default Map<Long,Long> findAllWithMovieCounts() {
        List<Object[]> l = new CopyOnWriteArrayList<>(findAllWithMovieCountsObjects());
        return l.parallelStream().collect(Collectors.toMap(o -> (Long) o[0], o -> (Long) o[1]));
    }
}
